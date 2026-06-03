package com.pao.laboratory14.exercise2.repository;

import com.pao.laboratory14.exercise1.TipBilet;
import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvenimentRepository implements Repository<Eveniment, Integer> {

    private final Connection connection;

    public EvenimentRepository() throws SQLException, IOException {
        this.connection = DatabaseConnection.getInstance().getConnection();
        initSchema();
    }

    public void initSchema() throws SQLException {

        String dropSql = "DROP TABLE IF EXISTS evenimente";

        String createSql = """
                CREATE TABLE IF NOT EXISTS evenimente (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nume TEXT NOT NULL,
                    data TEXT NOT NULL,
                    capacitate INTEGER,
                    tip TEXT
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropSql);
            statement.executeUpdate(createSql);
        }
    }

    @Override
    public void save(Eveniment entity) throws SQLException {

        String sql = """
                INSERT INTO evenimente(nume, data, capacitate, tip)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        )) {

            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getData());
            ps.setInt(3, entity.getCapacitate());
            ps.setString(4, entity.getTip().name());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<Eveniment> findById(Integer id) throws SQLException {

        String sql = "SELECT * FROM evenimente WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Eveniment> findAll() throws SQLException {

        List<Eveniment> evenimente = new ArrayList<>();

        String sql = "SELECT * FROM evenimente ORDER BY id";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
                evenimente.add(mapResultSet(rs));
            }
        }

        return evenimente;
    }

    @Override
    public void update(Eveniment entity) throws SQLException {

        String sql = """
                UPDATE evenimente
                SET nume = ?, data = ?, capacitate = ?, tip = ?
                WHERE id = ?
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getNume());
            ps.setString(2, entity.getData());
            ps.setInt(3, entity.getCapacitate());
            ps.setString(4, entity.getTip().name());
            ps.setInt(5, entity.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        deleteImpl(id);
    }

    public int deleteImpl(int id) throws SQLException {

        String sql = "DELETE FROM evenimente WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate();
        }
    }

    public int count() throws SQLException {

        String sql = "SELECT COUNT(*) FROM evenimente";

        try (
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    private Eveniment mapResultSet(ResultSet rs) throws SQLException {

        return new Eveniment(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("data"),
                rs.getInt("capacitate"),
                TipBilet.valueOf(rs.getString("tip"))
        );
    }
}
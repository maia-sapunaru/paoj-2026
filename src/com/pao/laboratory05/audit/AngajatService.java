package com.pao.laboratory05.audit;


import java.time.LocalDateTime;
import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati = new Angajat[0];
    private AuditEntry[] auditLog = new AuditEntry[0];

    private AngajatService(){}

    private static class Holder{
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance(){
        return Holder.INSTANCE;
    }

    private void logAction(String action, String target){
        AuditEntry entry = new AuditEntry(action, target, LocalDateTime.now().toString());

        AuditEntry[] a = new AuditEntry[auditLog.length + 1];
        System.arraycopy(auditLog, 0, a, 0, auditLog.length);
        a[a.length - 1] = entry;
        auditLog = a;
    }

    public void addAngajat(Angajat a){
        Angajat[] ang= new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, ang, 0, angajati.length);
        ang[ang.length - 1] = a;
        angajati = ang;

        System.out.println("Angajat adaugat: " + a.getNume());
        logAction("ADD", a.getNume());
    }

    public void printAll(){
        if(angajati.length == 0)
            System.out.println("Nu exitsa angajati");
        else {
            for (Angajat angajat : angajati) {
                System.out.println(angajat);
            }
        }
    }

    public void listBySalary(){
        if(angajati.length == 0)
            System.out.println("Nu exitsa angajati");
        else{
            Angajat[] copy = angajati.clone();
            Arrays.sort(copy);

            for(Angajat angajat : copy){
                System.out.println(angajat);
            }
        }
    }

    public void findDepartament(String numeDept){
        logAction("FIND_BY_DEPT", numeDept);

        boolean gasit = false;
        for(Angajat angajat : angajati){
            if(angajat.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(angajat);
                gasit = true;
            }
        }
        if(!gasit)
            System.out.println("Niciun angajat in departamentul: " + numeDept);
    }


    public void printAuditLog(){
        if(auditLog.length == 0){
            System.out.println("Audit log gol");
            return;
        }
        for(AuditEntry audit : auditLog){
            System.out.println(audit);
        }
    }

}

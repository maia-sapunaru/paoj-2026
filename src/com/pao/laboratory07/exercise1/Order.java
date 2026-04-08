package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.ArrayDeque;
import java.util.Deque;

public class Order {
    private OrderState currentState;
    private final Deque<OrderState> history;

    public Order(OrderState initialState){
        this.currentState = initialState;
        this.history = new ArrayDeque<>();
    }

    public void nextState() throws OrderIsAlreadyFinalException{
        OrderState previousState = currentState;
        OrderState nextState = currentState.next();
        history.push(previousState);
        currentState = nextState;
        System.out.println("Order state updated to: " + currentState);
    }

    public void cancel() throws CannotCancelFinalOrderException{
        OrderState previousState = currentState;
        OrderState cancelledState = currentState.cancel();
        history.push(previousState);
        currentState = cancelledState;
        System.out.println("Order has been canceled.");
    }

    public void undoState() throws CannotRevertInitialOrderStateException{
        if (history.isEmpty()){
            throw new CannotRevertInitialOrderStateException();
        }

        currentState = history.pop();
        System.out.println("Order state reverted to: " + currentState);
    }

    public OrderState getCurrentState(){
        return currentState;
    }
}
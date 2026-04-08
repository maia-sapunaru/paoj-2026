package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

public enum OrderState {
    PLACED {
        @Override
        public OrderState next(){
            return PROCESSED;
        }

        @Override
        public OrderState cancel(){
            return CANCELLED;
        }
    },
    PROCESSED {
        @Override
        public OrderState next(){
            return SHIPPED;
        }

        @Override
        public OrderState cancel(){
            return CANCELLED;
        }
    },
    SHIPPED {
        @Override
        public OrderState next(){
            return DELIVERED;
        }

        @Override
        public OrderState cancel(){
            return CANCELLED;
        }
    },
    DELIVERED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException{
            throw new OrderIsAlreadyFinalException();
        }

        @Override
        public OrderState cancel() throws CannotCancelFinalOrderException{
            throw new CannotCancelFinalOrderException();
        }
    },
    CANCELLED {
        @Override
        public OrderState next() throws OrderIsAlreadyFinalException{
            throw new OrderIsAlreadyFinalException();
        }

        @Override
        public OrderState cancel() throws CannotCancelFinalOrderException{
            throw new CannotCancelFinalOrderException();
        }
    };

    public abstract OrderState next() throws OrderIsAlreadyFinalException;

    public abstract OrderState cancel() throws CannotCancelFinalOrderException;
}
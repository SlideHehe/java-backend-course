package edu.hw6.task6;

public enum Protocol {
    UDP {
        @Override
        public String toString() {
            return "UDP";
        }
    },

    TCP {
        @Override
        public String toString() {
            return "TCP";
        }
    }
}

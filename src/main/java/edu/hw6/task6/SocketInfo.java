package edu.hw6.task6;

public record SocketInfo(Protocol protocol, int port, boolean available, String service) {
    @Override
    public String toString() {
        String status = available ? "Available" : "Taken";

        return String.format(
            "%8s\t" + "%5d\t" + "%9s\t%s",
            protocol, port, status, service
        );
    }
}

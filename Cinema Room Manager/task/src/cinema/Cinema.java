package cinema;

import java.util.Scanner;

public class Cinema {
    private static final Scanner scanner = new Scanner(System.in);
    private static int purchasedTickets = 0;
    private static int totalAvailableTickets = 0;
    private static int currentIncome = 0;
    private static int totalIncome = 0;

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int cols = scanner.nextInt();
        String[][] cinema = cinemaBuilder(rows, cols);
        calculateProfitFromSoldTickets(cinema);
        boolean stillBrowsing = true;
        while (stillBrowsing) {
            System.out.println();
            showActionMenu();
            int action = scanner.nextInt();
            switch (action) {
                case 1:
                    printSeatLayout(cinema);
                    break;
                case 2:
                    System.out.println("Enter a row number: ");
                    int seatRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row: ");
                    int seatNumber = scanner.nextInt();
                    bookSeat(cinema, seatRow, seatNumber);
                    break;
                case 3:
                    printStatistic();
                    break;
                case 0:
                    stillBrowsing = false;
                    break;
            }

        }
    }

    public static String[][] cinemaBuilder(int rows, int cols) {
        int rowSize = rows + 1;
        int colSize = cols + 1;
        totalAvailableTickets = rows * cols;
        String[][] cinema = new String[rowSize][colSize];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                if (i == 0 && j == 0) {
                    cinema[i][j] = "  ";
                } else if (i == 0) {
                    cinema[i][j] = j + " ";
                } else if (j == 0) {
                    cinema[i][j] = i + " ";
                } else {
                    cinema[i][j] = "S ";
                }
            }
        }
        return cinema;
    }

    public static void printSeatLayout(String[][] cinema) {
        System.out.println("Cinema:");
        for (String[] strings : cinema) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void bookSeat(String[][] cinema, int seatRow, int seatNumber) {
        boolean flag = true;
        while (true) {
            if ((seatRow >= 0 && seatRow < cinema.length) &&
                    (seatNumber < cinema[cinema.length - 1].length && seatNumber >= 0)) {
                if (cinema[seatRow][seatNumber].equals("B ")) {
                    System.out.println("That ticket has already been purchased!");
                    System.out.println();
                    System.out.println("Enter a row number:");
                    seatRow = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    seatNumber = scanner.nextInt();
                } else {
                    calculateTicketPrice(cinema, seatRow, seatNumber);
                    cinema[seatRow][seatNumber] = "B ";
                    break;
                }
            } else {
                System.out.println("Wrong input!");
                System.out.println();
                System.out.println("Enter a row number:");
                seatRow = scanner.nextInt();
                System.out.println("Enter a seat number in that row:");
                seatNumber = scanner.nextInt();
            }
        }

    }

    public static void showActionMenu() {
        System.out.println("1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
    }

    public static void printStatistic() {
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        double percentage = (double) ((double) purchasedTickets / (double) totalAvailableTickets) * 100;
        String percentageString = String.format("%.2f", percentage);
        System.out.println("Percentage: " + percentageString + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static void calculateProfitFromSoldTickets(String[][] cinema) {
        int numberOfRows = cinema.length - 1;
        int numberOfSeatsInEachRow = cinema[cinema.length - 1].length - 1;
        int totalSeats = numberOfRows * numberOfSeatsInEachRow;
        if (totalSeats < 60) {
            totalIncome += 10 * totalSeats;
        } else {
            int frontPricing = (numberOfRows / 2) * numberOfSeatsInEachRow * 10;
            int backPricing = (numberOfRows - (numberOfRows / 2)) * numberOfSeatsInEachRow * 8;
            totalIncome += frontPricing + backPricing;
        }
    }

    public static void calculateTicketPrice(String[][] cinema, int rows, int col) {
        int cinemaSize = cinema.length * cinema[cinema.length - 1].length;
        System.out.println();
        int ticketPrice = 0;
        if (cinemaSize < 60) {
            ticketPrice = 10;
        } else {
            if (rows < cinema.length / 2) {
                ticketPrice = 10;
            } else {
                ticketPrice = 8;
            }
        }
        purchasedTickets++;
        currentIncome += ticketPrice;
        System.out.println("Ticket price: $" + ticketPrice);
        System.out.println();
    }
}


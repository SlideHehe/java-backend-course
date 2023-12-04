package edu.project4.components;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Dimensions can't be less than 0");
        }

        Pixel[] data = new Pixel[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                data[y * width + x] = new Pixel(0, 0, 0, 0);
            }
        }

        return new FractalImage(data, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (contains(x, y)) {
            return data[y * width + x];
        }

        return null;
    }
}

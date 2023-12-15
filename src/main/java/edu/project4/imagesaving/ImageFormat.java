package edu.project4.imagesaving;

public enum ImageFormat {
    JPEG {
        @Override
        public String toString() {
            return "jpeg";
        }
    },
    BMP {
        @Override
        public String toString() {
            return "bmp";
        }
    },
    PNG {
        @Override
        public String toString() {
            return "png";
        }
    }
}

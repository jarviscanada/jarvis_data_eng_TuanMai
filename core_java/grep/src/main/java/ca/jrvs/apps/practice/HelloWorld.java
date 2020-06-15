package ca.jrvs.apps.practice;

class HelloWorld {
    public static void main(String args[]) {
        System.out.println("Hello, World!");


        // Regex Test
        RegexExcImp regex = new RegexExcImp();

        System.out.println(regex.matchJpeg("image.jpg"));
        System.out.println(regex.matchIp("123.456.789.159"));
        System.out.println(regex.isEmptyLine(" "));

    }
}
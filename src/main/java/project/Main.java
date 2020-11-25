package project;

public class Main {
    public static void main(String[] args) {
        MagnitBean magnitBean = new MagnitBean();
        magnitBean.setN(10000);
        magnitBean.setURL("jdbc:postgresql://localhost:5432/TEST");
        magnitBean.setUsername("root");
        magnitBean.setUsername("password");

        magnitBean.updateBase();
        magnitBean.creatXmlFile(magnitBean.loadBase());
        magnitBean.transformations();
        System.out.println(magnitBean.sumAttributes());
    }
}
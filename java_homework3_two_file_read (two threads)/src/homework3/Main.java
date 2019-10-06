package homework3;

public class Main {

    public static void main(String[] args) {

        CountryInfo countryInfo = new CountryInfo();
        CountryThread countryThread = new CountryThread(countryInfo);
        CapitalThread capitalThread = new CapitalThread(countryInfo);

        new Thread(countryThread).start();
        new Thread(capitalThread).start();
    }
}
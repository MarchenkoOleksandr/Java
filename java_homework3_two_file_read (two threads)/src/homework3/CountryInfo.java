package homework3;

import java.io.*;
import java.util.ArrayList;

public class CountryInfo {
    private String country;
    private String capital;

    CountryInfo() {
        this.country = null;
        this.capital = null;
    }

    private CountryInfo(String country, String capital) {
        this.country = country;
        this.capital = capital;
    }

    synchronized void getCountry() {
        try (BufferedReader countriesReader = new BufferedReader(new FileReader("Countries.txt"))) {
            while ((this.country = countriesReader.readLine()) != null) {
                notify();
                wait();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    synchronized void getCapital() {
        ArrayList<CountryInfo> countryList = new ArrayList<>();

        try (BufferedReader capitalReader = new BufferedReader(new FileReader("Capitals.txt"))) {
            while ((this.capital = capitalReader.readLine()) != null) {
                wait();
                countryList.add(new CountryInfo(this.country, this.capital));
                notify();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        SortListByCountries(countryList);
        PrintCountries(countryList);
        SaveToFile(countryList);
    }

    @Override
    public String toString() {
        return this.country + " - " + this.capital;
    }

    private void SortListByCountries(ArrayList<CountryInfo> countryList) {
        countryList.sort((a, b) -> a.country.compareToIgnoreCase(b.country));
    }

    private void PrintCountries(ArrayList<CountryInfo> countryList) {
        for (CountryInfo cl : countryList) {
            System.out.println(cl);
        }
    }

    private void SaveToFile(ArrayList<CountryInfo> countryList) {
        try (FileWriter fileWriter = new FileWriter("Countries and capitals.txt", false)) {
            for (CountryInfo cl : countryList) {
                fileWriter.write(cl.toString() + '\n');
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
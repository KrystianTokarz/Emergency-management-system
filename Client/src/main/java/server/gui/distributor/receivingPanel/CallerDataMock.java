package server.gui.distributor.receivingPanel;

/**
 * Mocking class which static methods for adding new caller into system
 */
public class CallerDataMock {

    public static String getLocation(int index){
        String[] locationArray = new String[10];
        locationArray[0] = "Lodz";
        locationArray[1] = "Sandomierz";
        locationArray[2] = "Kielce";
        locationArray[3] = "Kielce";
        locationArray[4] = "Sandomierz";
        locationArray[5] = "Lodz";
        locationArray[6] = "Lodz";
        locationArray[7] = "Sandomierz";
        locationArray[8] = "Kielce";
        locationArray[9] = "Lodz";
        return locationArray[index];
    }

    public static String getNumber(int index){
        String[] numberArray = new String[10];
        numberArray[0] = "123123123";
        numberArray[1] = "333333333";
        numberArray[2] = "412312413";
        numberArray[3] = "+486123123";
        numberArray[4] = "123561786";
        numberArray[5] = "651231519";
        numberArray[6] = "882527892";
        numberArray[7] = "246821737";
        numberArray[8] = "246872525";
        numberArray[9] = "+481114324";
        return numberArray[index];
    }
}

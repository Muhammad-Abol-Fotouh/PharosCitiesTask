package de.pharos_solutions.pharoscitiestask;

import org.junit.Test;
import java.util.ArrayList;
import de.pharos_solutions.pharoscitiestask.models.CityModel;
import static org.junit.Assert.*;

/**
 * Created by muhammadkorany on 10/6/17.
 */

public class SearchFunctionTest {

    public static class SequentialSearch {
        public static boolean contains(ArrayList<CityModel> cityModels, String query){
            for (CityModel i : cityModels) {
                if (i.getName().toLowerCase().toString().contains(query.toLowerCase())){
                        return true;
                }
            }
            return false;
        }
    }

    @Test
    public void searchForCity() throws Exception{

        ArrayList<CityModel> cityModels = new ArrayList<>();

        CityModel alexandria = new CityModel();
        alexandria.setName("Alexandria");
        cityModels.add(alexandria);

        CityModel cairo = new CityModel();
        cairo.setName("Cairo");
        cityModels.add(cairo);

        assertTrue(SequentialSearch.contains(cityModels, "Al"));
        assertTrue(SequentialSearch.contains(cityModels, cairo.getName()));
        assertFalse(SequentialSearch.contains(cityModels, "Aswan"));
    }
}

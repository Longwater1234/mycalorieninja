package org.davistiba;

import java.net.URI;
import java.net.URISyntaxException;

public class MakeURI {
    private URI finalLink;

    /*
     * https://api.calorieninjas.com/v1/nutrition?query=red%20apple
     *
     */

    public URI makeLink(String Query) {
        Query = "query=" + Query;
        try {
            finalLink = new URI("https",
                    "api.calorieninjas.com",
                    "/v1/nutrition",
                    Query, null);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return finalLink;

    }
}

/*
 * //JAVA:
 * resultObject.getJSONArray("items").getJSONObject(0)
 *
 * //JS:
 * resultobject.items[0]
 *
 */


package fr.nicolasgodefroy.ohmybeer.controller;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import fr.nicolasgodefroy.ohmybeer.Constante;
import fr.nicolasgodefroy.ohmybeer.model.Brewery;
import fr.nicolasgodefroy.ohmybeer.model.Country;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nicolasgodefroy on 14/04/2016.
 */
@RestController
@RequestMapping("/brewerycountry")
public class BreweryCountry {


    private final Bucket bucket;

    private final String LIMIT = "50";

    public BreweryCountry() {
        Cluster cluster = CouchbaseCluster.create(Constante.IP);
        bucket = cluster.openBucket(Constante.DB);
    }

    @RequestMapping(value = "/country/", method = RequestMethod.GET)
    private List<Country> getCountry() {
        String query = "SELECT DISTINCT(country) FROM `beer-sample`";
        List<Country> countries = executeQuery(query);
        return countries;
    }

    private List<Country> executeQuery(String query) {
        N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(query));
        Iterator<N1qlQueryRow> it = queryResult.rows();
        List<Country> tmp = new ArrayList<>();
        while (it.hasNext()) {
            N1qlQueryRow row = it.next();
            final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            final String country1 = mapper.convertValue(row.value().toMap().get("country"), String.class);
            if (country1 != null && country1.length() > 0) {
                Country country = new Country();
                country.setName(country1);
                country.setImageUrl("http://flags.fmcdn.net/data/flags/small/" + country1.substring(0, 2).toLowerCase() + ".png");
                tmp.add(country);
            }
        }
        return tmp;
    }
}

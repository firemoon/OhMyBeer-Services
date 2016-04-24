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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nicolasgodefroy on 10/04/2016.
 */
@RestController
@RequestMapping("/whereismybrewery")
public class WhereIsMyBrewery {

    private final Bucket bucket;

    private final String LIMIT = "50";

    public WhereIsMyBrewery() {
        Cluster cluster = CouchbaseCluster.create(Constante.IP);
        bucket = cluster.openBucket(Constante.DB);
    }

    @RequestMapping(value = "/country/{countryName}", method = RequestMethod.GET)
    List<Brewery> getBreweryWithCountryName(@PathVariable String countryName) {
        String query = "SELECT * FROM `beer-sample` WHERE  country = \"" + countryName + "\" LIMIT " + LIMIT + ";";
        return executeQuery(query);
    }

    @RequestMapping(value = "/city/{cityName}", method = RequestMethod.GET)
    List<Brewery> getBreweryWithCityName(@PathVariable String cityName) {
        String query = "SELECT * FROM `beer-sample` WHERE  city = \"" + cityName + "\" LIMIT " + LIMIT + ";";
        return executeQuery(query);
    }


    @RequestMapping(value = "/brewery/{breweryId}", method = RequestMethod.GET)
    Brewery getBreweryWithBreweryId(@PathVariable String breweryId) {
        String query = "SELECT * FROM `beer-sample` WHERE  brewery_id = \"" + breweryId + "\" LIMIT 1;";
        return executeQuery(query).get(0);
    }


    private List<Brewery> executeQuery(String query) {
        N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(query));
        Iterator<N1qlQueryRow> it = queryResult.rows();
        List<Brewery> tmp = new ArrayList<>();
        while (it.hasNext()) {
            N1qlQueryRow row = it.next();
            final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            final Brewery brewery = mapper.convertValue(row.value().toMap().get("beer-sample"), Brewery.class);
            tmp.add(brewery);
            System.out.println(row.value());
        }
        return tmp;
    }
}
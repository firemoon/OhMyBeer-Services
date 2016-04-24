package fr.nicolasgodefroy.ohmybeer.controller;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import fr.nicolasgodefroy.ohmybeer.Constante;
import fr.nicolasgodefroy.ohmybeer.model.Beer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nicolasgodefroy on 10/04/2016.
 */
@RestController
@RequestMapping("/whereismybeer")
public class WhereIsMyBeer {

    private final Bucket bucket;

    private final String LIMIT = "1000";

    public WhereIsMyBeer() {
        Cluster cluster = CouchbaseCluster.create(Constante.IP);
        bucket = cluster.openBucket(Constante.DB);
    }

    @RequestMapping(value = "/brewery/{breweryId}", method = RequestMethod.GET)
    List<Beer> getBeerWithBreweryId(@PathVariable String breweryId) {
        breweryId = breweryId.replaceAll(" ", "_").toLowerCase();
        String query = "SELECT * FROM `beer-sample` WHERE  brewery_id = \"" + breweryId + "\" AND type = 'beer' LIMIT " + LIMIT + ";";
        return executeQuery(query);
    }


    @RequestMapping(value = "/abv", method = RequestMethod.GET)
    List<Beer> getBeerWithAbv(@RequestParam(name = "abv", required = true, defaultValue = "0") String abv) {
        String query = "SELECT * FROM `beer-sample` WHERE  abv = " + abv + " AND type = 'beer' LIMIT " + LIMIT + ";";
        return executeQuery(query);
    }


    @RequestMapping(value = "/beerfilter", method = RequestMethod.GET)
    List<Beer> getBeerWithFilter(@RequestParam(name = "abvMax", defaultValue = "100") String abvMax,
                                 @RequestParam(name = "abvMin", defaultValue = "0") String abvMin,
                                 @RequestParam(name = "breweryId", required = false) String breweryId) {

        String query = "SELECT * FROM `beer-sample` WHERE  abv < " + abvMax + " AND abv > " + abvMin + " AND type = 'beer' ORDER BY abv ASC LIMIT " + LIMIT + ";";
        if (breweryId != null) {
            breweryId = breweryId.replaceAll(" ", "_").toLowerCase();
            query = "SELECT * FROM `beer-sample` WHERE  abv < " + abvMax + " AND abv > " + abvMin + " AND brewery_id = \"" + breweryId + "\" AND type = 'beer' ORDER BY abv ASC LIMIT " + LIMIT + ";";
        }
        return executeQuery(query);
    }


    private List<Beer> executeQuery(String query) {
        N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(query));
        Iterator<N1qlQueryRow> it = queryResult.rows();
        List<Beer> tmp = new ArrayList<>();
        while (it.hasNext()) {
            N1qlQueryRow row = it.next();
            final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            final Beer beer = mapper.convertValue(row.value().toMap().get("beer-sample"), Beer.class);
            tmp.add(beer);
            System.out.println(row.value());
        }
        return tmp;
    }
}
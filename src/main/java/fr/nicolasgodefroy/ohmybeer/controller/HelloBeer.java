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
import fr.nicolasgodefroy.ohmybeer.model.Brewery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by nicolasgodefroy on 10/04/2016.
 */
@RestController
@RequestMapping("/hellobeer")
public class HelloBeer {


    private final Bucket bucket;

    public HelloBeer() {
        Cluster cluster = CouchbaseCluster.create(Constante.IP);
        bucket = cluster.openBucket(Constante.DB);
    }


    @RequestMapping("/")
    public Beer home() {
        String query = "SELECT * FROM `beer-sample` WHERE  type = 'beer' LIMIT 1;";
        N1qlQueryResult queryResult = bucket.query(N1qlQuery.simple(query));
        Iterator<N1qlQueryRow> it = queryResult.rows();
        List<Beer> tmp = new ArrayList<>();
        while (it.hasNext()) {
            N1qlQueryRow row = it.next();
            final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
            Map<String, Object> map = (Map<String, Object>)row.value().toMap().get("beer-sample");
            final Beer beer = mapper.convertValue(map, Beer.class);
            tmp.add(beer);
            System.out.println(row.value());
        }
        return tmp.get(0);
    }
}
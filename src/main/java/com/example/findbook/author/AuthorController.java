package com.example.findbook.author;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;


@Controller
public class AuthorController {

    private final AuthorRepository authRepo;

    public AuthorController(AuthorRepository authRepo){
        this.authRepo = authRepo;
    }
    @RequestMapping("/")
    public String welcome(){
        return "index";
    }

    @RequestMapping("/find")
    public String show(Model model){
        return "search";
    }

    @PostMapping("/search")
    @ResponseBody
    public String search(@RequestParam("query") String query){
        // get the record from database. If record does not exist, get it with api and
        // save that api record to the database

        // Check the database first...
        

        Optional<Author> author = authRepo.findByAuthorKey(query);
        System.out.println(author);
        
        if(!author.isEmpty()){
            // No author record with this id in database...
            HttpClient client = HttpClient.newHttpClient();
            HttpClient name = HttpClient.newHttpClient();

            try {
                // HTTP isteği oluştur

                // If looking for author's works;
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://openlibrary.org/authors/%s/works.json",query)))
                    .GET()
                    .build();
                
                    
                // İsteği gönder ve yanıtı al
                HttpResponse<String> authors_works = client.send(request, HttpResponse.BodyHandlers.ofString());
                

                HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://openlibrary.org/authors/%s.json",query)))
                .GET()
                .build();

                HttpResponse<String> name_response = client.send(req, HttpResponse.BodyHandlers.ofString());
                JSONObject jsonObject = new JSONObject(name_response.body());
                
                
                String authorName = jsonObject.getString("name");
                String author_key = query;
                
                // Get the record and apply it to the database...
                Author c_author = new Author(null, author_key, authorName);
                authRepo.create(c_author);

                // test

                System.out.println(authRepo.findAll());

                // Show the record...
                return authors_works.body();
            } catch (Exception e) {
                System.out.println(e);
            }
            return "oldu";

        }else{

            HttpClient client = HttpClient.newHttpClient();
            try{

                
                // If looking for author's works;
                HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://openlibrary.org/authors/%s/works.json",query)))
                .GET()
                .build();
                
                
                // İsteği gönder ve yanıtı al
                HttpResponse<String> authors_works = client.send(request, HttpResponse.BodyHandlers.ofString());

                return authors_works.body();
            }catch (Exception e) {
                System.out.println(e);
            }
            return "oldu";
        }
        
        // ------------------------------------------------
        

    }


    @PostMapping("/searchByName")
    @ResponseBody
    public String searchByName(@RequestParam("query") String query){
        // get the record from database. If record does not exist, get it with api and
        // save that api record to the database

        // Check the database first...
        

        Optional<Author> author = authRepo.findByAuthorName(query);
        System.out.println(author);
        
        if(author.isEmpty()){
            // No author record with this id in database...
            HttpClient client = HttpClient.newHttpClient();
            HttpClient name = HttpClient.newHttpClient();

            try {
                // HTTP isteği oluştur

                String url_query = URLEncoder.encode(query, StandardCharsets.UTF_8);

                
                System.out.println(url_query);

                // If looking for author's works by name;
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://openlibrary.org/search/authors.json?q=%s",url_query)))
                    .GET()
                    .build();
                
                    
                // İsteği gönder ve yanıtı al
                HttpResponse<String> authors = client.send(request, HttpResponse.BodyHandlers.ofString());
                

                JSONObject jsonObject = new JSONObject(authors.body());
                JSONArray docsArray  = jsonObject.getJSONArray("docs");
                JSONObject doc = docsArray.getJSONObject(0);
                String nm = doc.getString("name");
                String ky = doc.getString("key");
                

                // Get author's works by Id and return;
                HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://openlibrary.org/authors/%s/works.json",ky)))
                .GET()
                .build();

                HttpResponse<String> authors_works = client.send(request2, HttpResponse.BodyHandlers.ofString());


                // İsteği gönder ve yanıtı al
                
                // Get the record and apply it to the database...
                Author c_author = new Author(null, ky, nm);
                authRepo.create(c_author);



                // Show the record...
                return authors_works.body();
            } catch (Exception e) {
                System.out.println(e);
            }
            return "oldu";

        }else{
            HttpClient client = HttpClient.newHttpClient();
            HttpClient name = HttpClient.newHttpClient();
            try {
                String url_query = URLEncoder.encode(query, StandardCharsets.UTF_8);

                
                System.out.println(url_query);

                // If looking for author's works by name;
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(String.format("https://openlibrary.org/search/authors.json?q=%s",url_query)))
                    .GET()
                    .build();
                
                    
                // İsteği gönder ve yanıtı al
                HttpResponse<String> authors = client.send(request, HttpResponse.BodyHandlers.ofString());
                

                JSONObject jsonObject = new JSONObject(authors.body());
                JSONArray docsArray  = jsonObject.getJSONArray("docs");
                JSONObject doc = docsArray.getJSONObject(0);
                String nm = doc.getString("name");
                String ky = doc.getString("key");
                

                // Get author's works by Id and return;
                HttpRequest request2 = HttpRequest.newBuilder()
                .uri(new URI(String.format("https://openlibrary.org/authors/%s/works.json",ky)))
                .GET()
                .build();

                HttpResponse<String> authors_works = client.send(request2, HttpResponse.BodyHandlers.ofString());   
                
                return authors_works.body();
            } catch (Exception e) {
                System.out.println(e);

            }
            
            
            
            return "oldu";
        }
        
    }
}

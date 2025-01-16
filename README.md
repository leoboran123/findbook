This program does requests from - https://openlibrary.org/dev/docs/api/authors - API and returns it to the user. 

You can use an author id or author name to get author's all the works.
Simply to do that, go to the /find URL. If you want to make a query with author id, use "Find author by author ID" section. Otherwise, use "Find author by name".

Program is going to do the query in the database first. Then if it doesn't find any record in database, it is going to use the API. Then going to record the author to the database.

Database only contains author id and author name. I didn't have much time to add more functionality to the program.

<h1>Insight about project</h1>

<p>* I couldn't use Hibernate and MySql database in this project.</p>
<br>
<p>* Thymeleaf used for templates</p>
<br>
<p>- Author.java file for Author model which makes database queries much easier and only contains author name and author key. </p>
<br>
<p>- AuthorController.java file is our only controller in entire project. Contains welcome, show, search and searchByName functions.

When you click the button above, it redirects you to '/find' path. This path is connected to the show function which only returns 
search.html (which contains our forms).

After the redirection, if you choose to make query with author id, form sends id to the search function. In here, I did some hard coding 
and repeated myself over and over. It sends the query to the database through model first. If return is empty, program chooses the API to get 
datas and saves the author name and author key to the database. If it's not empty, it does the exact same things except database steps :).

In searchByName function, I've faced with a problem. In the form, we take the author name with spaces (like = jk rowling). While doing the 
API query, I had to make this String suitable for URL GET query parameter. Therefore, I've used URLEncoder to change spaces and any other special
characters suitable to be parameters for URL. ("jk rowling" ===> "jk%20rowling") 
</p>
<br>
<p>- AuthorRepository.java file is basically the connection between Author and AuthorController. We use AuthorRepository in the controller to make database queries easier and sql free.
</p>
<br>
<p>- application.properties file contains our program's settings such as name, database connection informations etc. As you can see from there, I have used h2 connection for my database. You can check the database with given informations in this file.</p>
<br>
<hr>

This program does requests from - https://openlibrary.org/dev/docs/api/authors - API and returns it to the user. 

You can use an author id or author name to get author's all the works.
Simply to do that, go to the /find URL. If you want to make a query with author id, use "Find author by author ID" section. Otherwise, use "Find author by name".

Program is going to do the query in the database first. Then if it doesn't find any record in database, it is going to use the API. Then going to record the author to the database.

Database only contains author id and author name. I didn't have much time to add more functionality to the program.

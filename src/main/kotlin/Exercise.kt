import resources.Data

/**

# Fueled Kotlin Exercise

A blogging platform stores the following information that is available through separate API endpoints:
+ user accounts
+ blog posts for each user
+ comments for each blog post

### Objective
The organization needs to identify the 3 most engaging bloggers on the platform. Using only Kotlin and the Kotlin standard library, output the top 3 users with the highest average number of comments per post in the following format:

`[name]` - `[id]`, Score: `[average_comments]`

Instead of connecting to a remote API, we are providing this data in form of JSON files, which have been made accessible through a custom Resource enum with a `data` method that provides the contents of the file.

### What we're looking to evaluate
1. How you choose to model your data
2. How you transform the provided JSON data to your data model
3. How you use your models to calculate this average value
4. How you use this data point to sort the users

 */

// 1. First, start by modeling the data objects that will be used.
// TODO it would be better to have separate file for data class
data class UserInfo(
        var id: String = "",
        var name: String = "",
        var userName: String = "",
        var email: String = "",
        var address: Address = Address(),
        var phone: String = "",
        var website: String = "",
        var company: Company = Company(),
        var postList: List<Post> = listOf(),
        var averageComments: Int
)

data class Address(
        var street: String = "",
        var suit: String = "",
        var city: String = "",
        var zipCode: String = "",
        var geo: Geo = Geo()
)

data class Geo(
        var lat: String = "",
        var lng: String = ""
)

data class Company(
        var name: String = "",
        var catchPhrase: String = "",
        var bs: String = ""
)

data class Post(
        var userId: String = "",
        var id: String = "",
        var title: String = "",
        var body: String = "",
        var comments: List<Comment> = listOf()
)

data class Comment(
        var postId: String = "",
        var email: String = "",
        var id: String = "",
        var name: String = "",
        var body: String = ""
)

data class UserOutputInfo(
        var userNameAndId: String = "",
        var score: String = ""
)

fun main(vararg args: String) {
    // 2. Next, decode the JSON source using `[Data.getUsers()]`
    val users: Array<UserInfo> = Data.getUsers()
    val posts: Array<Post> = Data.getPosts()
    val comments: Array<Comment> = Data.getPosts()

    for (post in posts) {
        post.comments = comments.filter { it.postId == post.id }
    }
    for (user in users) {
        user.postList = posts.filter { it.userId == user.id }
    }

    // 3. Finally, calculate the average number of comments per user and use it
    val userList: ArrayList<UserInfo> = ArrayList()
    for (i in users.indices) {
        val user = users[i]
        var userComments = 0
        for (post in user.postList) {
            userComments += post.comments.size
        }
        user.averageComments = userComments / user.postList.size
        userList.add(user)
    }

    //    to find the 3 most engaging bloggers and output the result.
    userList.sortByDescending { it.averageComments }
    val userOutputInfoList: ArrayList<UserOutputInfo> = ArrayList()
    for (i in 1..2) {
        val userNameAndId = userList[i].userName + " - " + userList[i].id
        val score = "Score: " + userList[i].averageComments
        val userOutputInfo = UserOutputInfo(userNameAndId, score)
        //userOutputInfoList is the final result
        userOutputInfoList.add(userOutputInfo)
    }
}
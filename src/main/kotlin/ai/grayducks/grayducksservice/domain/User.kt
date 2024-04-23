package ai.grayducks.grayducksservice.domain

data class User(val id: String,
                val email: String,
                val name: String,
                val verified_email: Boolean?,
                val given_name: String?,
                val picture: String?,
                val locale: String?) {
}
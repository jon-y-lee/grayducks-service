package ai.grayducks.grayducksservice.service

data class UserProfile(val id: String?,
                       val email: String?,
                       val name: String?,
                       val verified_email: Boolean?,
                       val given_name: String?,
                       val picture: String?,
                       val locale: String?) {
}
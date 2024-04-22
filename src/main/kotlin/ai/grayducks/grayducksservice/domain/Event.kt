package ai.grayducks.grayducksservice.domain

data class EventResponse(
    val items: List<Event>?,
    val summary: String?
    ) {
}

data class Event(
    val status: String?,
    val start: EventDateTime?,
    val end: EventDateTime?,
    val summary: String?,
    val recurrence: String?,
    val description: String?,
    val colorId: Int) {
}

data class EventDateTime (
    val dateTime: String,
    val timeZone: String
) {

}
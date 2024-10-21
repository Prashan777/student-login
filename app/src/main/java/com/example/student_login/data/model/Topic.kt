data class TopicData(
    val entities: List<TopicTechnique>,
    val entityTotal: Int
)

data class TopicTechnique(
    val technique: String,
    val equipment: String,
    val subject: String,
    val pioneeringPhotographer: String,
    val yearIntroduced: Int,
    val description: String
)

package com.whoismacy.android.incidentincidence.view

import android.content.Context
import android.content.Intent
import com.whoismacy.android.incidentincidence.model.Incident
import com.whoismacy.android.incidentincidence.utils.dateToHumanReadable

fun shareIncident(
    context: Context,
    incident: Incident,
) {
    val incidentContent = createInfo(incident)
    val incidentIntent =
        Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, incidentContent)
        }
    val chooserIntent =
        Intent
            .createChooser(incidentIntent, "New Incident #${incident.id}")
    context.startActivity(chooserIntent)
}

private fun createInfo(incident: Incident): String {
    val content =
        """
        Incident # ${incident.id}
        -------------------------
        ${incident.content}
        Reported On: ${dateToHumanReadable(incident.dateAdded)}
        ${if (incident.resolved) {
            "Status: Resolved on ${dateToHumanReadable(checkNotNull(incident.dateResolved))}"
        } else {
            "Status: Unresolved"
        }}
        """.trimIndent()
    return content
}

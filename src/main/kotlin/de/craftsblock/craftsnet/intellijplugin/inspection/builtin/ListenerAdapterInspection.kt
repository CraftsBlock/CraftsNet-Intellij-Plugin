package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.RequireImplementationInspectionRule

class ListenerAdapterInspection : CustomAnnotatedInspection(
    "Listener",
    "de.craftsblock.craftscore.event.EventHandler",
    ParameterInspectionRule(0, "event", "de.craftsblock.craftscore.event.Event"),
    RequireImplementationInspectionRule("de.craftsblock.craftscore.event.ListenerAdapter")
)
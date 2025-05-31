package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class EventTemplates : CustomDialogTemplateAction(
    "Event",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Listener", AllIcons.FileTypes.Java, CraftsNetTemplates.LISTENER),
    TemplateKind("Event", AllIcons.FileTypes.Java, CraftsNetTemplates.EVENT),
    TemplateKind("Cancellable Event", AllIcons.FileTypes.Java, CraftsNetTemplates.CANCELLABLE_EVENT)
)
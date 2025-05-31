package de.craftsblock.craftsnet.intellijplugin.templates.impl

import com.intellij.icons.AllIcons
import de.craftsblock.craftsnet.intellijplugin.templates.CraftsNetTemplates
import de.craftsblock.craftsnet.intellijplugin.templates.CustomDialogTemplateAction
import de.craftsblock.craftsnet.intellijplugin.templates.TemplateKind

class HttpTemplates : CustomDialogTemplateAction(
    "Http",
    "",
    AllIcons.FileTypes.Java,
    TemplateKind("Body", AllIcons.FileTypes.Java, CraftsNetTemplates.BODY),
    TemplateKind("Body parser", AllIcons.FileTypes.Java, CraftsNetTemplates.BODY_PARSER),
    TemplateKind("Session driver", AllIcons.FileTypes.Java, CraftsNetTemplates.SESSION_DRIVER),
    TemplateKind("Stream encoder", AllIcons.FileTypes.Java, CraftsNetTemplates.STREAM_ENCODER),
    TemplateKind("Transformer", AllIcons.FileTypes.Java, CraftsNetTemplates.TRANSFORMER)
)
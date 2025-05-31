package de.craftsblock.craftsnet.intellijplugin.templates

import org.jetbrains.annotations.NotNull
import javax.swing.Icon

class SingletonTemplateAction(templateKind: TemplateKind) : CustomTemplateAction(templateKind) {

    constructor(
        templateName: String,
        icon: Icon,
        @NotNull extension: String = templateName.split(".").last(),
        @NotNull visibility: TemplateVisibility = TemplateVisibility.JAVA_SOURCES,
        @NotNull liveTemplate: Boolean = true,
        @NotNull dynamicName: Boolean = (visibility == TemplateVisibility.JAVA_SOURCES)
    ) : this(TemplateKind(templateName, icon, templateName, extension, visibility, liveTemplate, dynamicName))

}
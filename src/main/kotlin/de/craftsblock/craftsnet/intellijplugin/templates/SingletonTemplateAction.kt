package de.craftsblock.craftsnet.intellijplugin.templates

import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag
import org.jetbrains.annotations.NotNull
import javax.swing.Icon

class SingletonTemplateAction(templateKind: TemplateKind) : CustomTemplateAction(templateKind) {

    constructor(
        templateName: String,
        icon: Icon,
        extension: String = templateName.split(".").last(),
        visibility: TemplateVisibility = TemplateVisibility.JAVA_SOURCES,
        liveTemplate: Boolean = true,
        dynamicName: Boolean = (visibility == TemplateVisibility.JAVA_SOURCES),
        featureFlag: FeatureFlag = FeatureFlag.BASE,
        args: Map<String, String> = mapOf(),
    ) : this(
        TemplateKind(
            templateName, icon, templateName, extension, visibility, liveTemplate, dynamicName, featureFlag, args
        )
    )

}
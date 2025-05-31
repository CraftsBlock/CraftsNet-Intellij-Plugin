package de.craftsblock.craftsnet.intellijplugin.schemas

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory
import com.jetbrains.jsonSchema.extension.SchemaType
import de.craftsblock.craftsnet.intellijplugin.uitls.CraftsNetVersionUtils

class AddonJsonSchemaProvider : JsonSchemaProviderFactory {

    override fun getProviders(project: Project): MutableList<JsonSchemaFileProvider> {
        return mutableListOf(object : JsonSchemaFileProvider {
            override fun getName(): String = "Addon json schema"
            override fun isAvailable(file: VirtualFile): Boolean =
                CraftsNetVersionUtils.isAvailable(project) && file.name.equals("addon.json", ignoreCase = true)

            override fun getSchemaFile(): VirtualFile? = JsonSchemaProviderFactory.getResourceFile(this::class.java, "/schemas/addon.json")
            override fun getSchemaType(): SchemaType = SchemaType.embeddedSchema
            override fun getRemoteSource(): String? = null
        })
    }

}
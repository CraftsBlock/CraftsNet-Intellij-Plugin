package de.craftsblock.craftsnet.intellijplugin.schemas

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.icons.AllIcons
import com.intellij.json.psi.JsonProperty
import com.intellij.json.psi.JsonStringLiteral
import com.intellij.openapi.module.ModuleUtil
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.ClassInheritorsSearch
import com.intellij.psi.util.InheritanceUtil
import com.intellij.util.ProcessingContext
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

class AddonJsonReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(JsonStringLiteral::class.java).withParent(
                PlatformPatterns.psiElement(JsonProperty::class.java).withName("main")
            ),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
                    val literal = element as JsonStringLiteral
                    val project = literal.project
                    val className = literal.value

                    if (!CraftsNetVersionUtils.isAvailable(element.project)) return arrayOf()

                    val module = ModuleUtil.findModuleForPsiElement(element) ?: return arrayOf()
                    val scope = GlobalSearchScope.moduleScope(module)

                    // Dateiüberprüfung: Nur wenn die Datei "addon.json" heißt
                    val containingFile = literal.containingFile
                    if (containingFile.name != "addon.json") {
                        return PsiReference.EMPTY_ARRAY
                    }

                    val psiFacade = JavaPsiFacade.getInstance(project)
                    val baseClass = psiFacade.findClass("de.craftsblock.craftsnet.addon.Addon", GlobalSearchScope.allScope(project))
                        ?: return PsiReference.EMPTY_ARRAY

                    return arrayOf(object : PsiReferenceBase<JsonStringLiteral>(literal, true) {

                        override fun resolve(): PsiElement? {
                            val clazz = psiFacade.findClass(className, scope)
                            return if (clazz != null && InheritanceUtil.isInheritor(
                                    clazz,
                                    baseClass.qualifiedName ?: baseClass.name!!
                                )
                            ) clazz else null
                        }

                        override fun getVariants(): Array<Any> {
                            return ClassInheritorsSearch.search(baseClass, scope, true)
                                .mapNotNull { it.qualifiedName }
                                .map { createLookupElement(it) }
                                .toTypedArray()
                        }

                        private fun createLookupElement(className: String): LookupElement {
                            return LookupElementBuilder.create(className)
                                .withIcon(AllIcons.FileTypes.Java)
                                .withTypeText("Addon", true)
                                .bold()
                        }

                    })
                }
            }
        )
    }
}

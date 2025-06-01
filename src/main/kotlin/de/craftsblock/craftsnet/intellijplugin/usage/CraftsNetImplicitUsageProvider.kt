package de.craftsblock.craftsnet.intellijplugin.usage

import com.intellij.codeInsight.AnnotationUtil
import com.intellij.codeInsight.daemon.ImplicitUsageProvider
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiModifierListOwner
import com.intellij.psi.util.InheritanceUtil
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils

private val SUPPORTED_ANNOTATIONS: Set<String> = setOf(
    "de.craftsblock.craftscore.event.EventHandler",
    "de.craftsblock.craftsnet.api.http.annotations.Route",
    "de.craftsblock.craftsnet.api.websocket.annotations.Socket",
    "de.craftsblock.craftsnet.autoregister.meta.AutoRegister"
)

private val SUPPORTED_SUPER_CLASSES: Set<String> = setOf(
    "de.craftsblock.craftsnet.addon.Addon",
    "de.craftsblock.craftscore.event.ListenerAdapter"
)

class CraftsNetImplicitUsageProvider : ImplicitUsageProvider {

    override fun isImplicitUsage(element: PsiElement): Boolean =
        CraftsNetVersionUtils.isAvailable(element.project) && (isAnnotated(element) || isSubclassOrImplements(element))

    override fun isImplicitRead(element: PsiElement): Boolean = false
    override fun isImplicitWrite(element: PsiElement): Boolean = false

    private fun isAnnotated(element: PsiElement): Boolean {
        if (element !is PsiModifierListOwner) return false
        val owner: PsiModifierListOwner = element
        for (annotation in SUPPORTED_ANNOTATIONS)
            if (AnnotationUtil.isAnnotated(owner, annotation, 0))
                return true
        return false
    }

    private fun isSubclassOrImplements(element: PsiElement): Boolean {
        if (element !is PsiClass) return false
        for (superClass in SUPPORTED_SUPER_CLASSES)
            if (InheritanceUtil.isInheritor(element, superClass))
                return true
        return false
    }

}
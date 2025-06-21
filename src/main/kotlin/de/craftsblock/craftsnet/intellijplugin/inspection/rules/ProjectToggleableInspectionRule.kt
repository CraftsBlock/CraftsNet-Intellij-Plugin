package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.State
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.StateListener
import java.util.function.BiFunction

open class ProjectToggleableInspectionRule(
    open val rule1: CustomInspectionRule,
    open val rule2: CustomInspectionRule,
    val cacheable: Boolean = true,
    val function: BiFunction<Project, ProjectToggleableInspectionRule, CustomInspectionRule?>,
) : CustomInspectionRule() {

    private val clearCacheListener: StateListener = object : StateListener {
        override fun discardedState(project: Project) = clearCache()
        override fun updatedState(project: Project, state: State) = updateCache(project)
    }

    init {
        CraftsNetVersionUtils.addStateListener(clearCacheListener)
    }

    private var lastProject: Project? = null
    private var cachedRule: CustomInspectionRule? = null

    fun updateCache(project: Project) {
        println("Updating cache")
        if (!cacheable || project != lastProject) return

        lastProject = project
        cachedRule = function.apply(project, this)
    }

    fun clearCache() {
        println("Clearing cache")

        lastProject = null
        cachedRule = null
    }

    override fun adopt(parent: CustomInspection) {
        super.adopt(parent)

        rule1.adopt(parent)
        rule2.adopt(parent)
    }

    override fun checkMethod(holder: ProblemsHolder, method: PsiMethod) {
        val project: Project = holder.project

        if (cacheable && handleCachedCheckMethod(project, holder, method))
            return

        handleUncachedCheckMethod(project, holder, method)
    }

    private fun handleCachedCheckMethod(project: Project, holder: ProblemsHolder, method: PsiMethod): Boolean {
        if (this.lastProject == null || this.cachedRule == null) return false
        if (this.lastProject != project) return false

        cachedRule!!.checkMethod(holder, method)
        return true
    }

    private fun handleUncachedCheckMethod(project: Project, holder: ProblemsHolder, method: PsiMethod) {
        val rule: CustomInspectionRule = function.apply(project, this) ?: return

        if (!rule.isAdopted())
            rule.adopt(this.parent!!)

        rule.checkMethod(holder, method)

        if (!cacheable) return
        lastProject = project
        cachedRule = rule
    }

}
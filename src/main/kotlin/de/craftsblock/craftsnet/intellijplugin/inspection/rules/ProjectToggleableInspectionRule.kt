package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.CraftsNetVersionUtils
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.State
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.StateListener
import java.util.function.BiFunction

open class ProjectToggleableInspectionRule<T : CustomInspectionRule>(
    open val rule1: T,
    open val rule2: T,
    val cacheable: Boolean = true,
    val function: BiFunction<Project, ProjectToggleableInspectionRule<T>, T?>,
) : CustomInspectionRule() {

    private val clearCacheListener: StateListener = object : StateListener {
        override fun discardedState(project: Project) = clearCache()
        override fun updatedState(project: Project, state: State) = updateCache(project)
    }

    init {
        CraftsNetVersionUtils.addStateListener(clearCacheListener)
    }

    private var lastProject: Project? = null
    private var cachedRule: T? = null

    fun updateCache(project: Project) {
        if (!cacheable || project != lastProject) return

        lastProject = project
        cachedRule = function.apply(project, this)
    }

    fun clearCache() {
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
        val rule: T = function.apply(project, this) ?: return

        if (!rule.isAdopted())
            rule.adopt(this.parent!!)

        rule.checkMethod(holder, method)

        if (!cacheable) return
        lastProject = project
        cachedRule = rule
    }

}
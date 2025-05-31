package de.craftsblock.craftsnet.intellijplugin.uitls

import com.intellij.psi.*

object Utils {

    private val dynamicUrlParamExtractor: Regex = Regex("\\{([^{}]+)}")

    internal fun getDynamicUrlParams(regex: String): MutableList<String> {
        val groupNames: LinkedHashSet<String> = LinkedHashSet()

        val matcher: Sequence<MatchResult> = dynamicUrlParamExtractor.findAll(regex)
        for (match in matcher)
            groupNames.add(match.groups[1]?.value ?: continue)

        return mutableListOf(*groupNames.toTypedArray())
    }

    internal fun collectAnnotation(method: PsiMethod, single: String, repeatable: String? = null): List<PsiAnnotation> {
        val direct = method.annotations.filter { it.qualifiedName == single }
        if (repeatable == null) return direct

        val container = method.getAnnotation(repeatable)
        val fromContainer = (container?.findDeclaredAttributeValue("value") as? PsiArrayInitializerMemberValue)
            ?.initializers
            ?.filterIsInstance<PsiAnnotation>()
            ?.filter { it.qualifiedName == single }
            ?: emptyList()

        return direct + fromContainer
    }

    internal fun typeToQualifiedName(type: PsiType): String? {
        return when (type) {
            is PsiPrimitiveType -> type.canonicalText
            is PsiClassType -> type.resolve()?.qualifiedName
            else -> type.canonicalText
        }
    }

    internal fun isPrimitive(type: PsiType): Boolean = getPrimitive(type) != null
    internal fun getPrimitive(type: PsiType): PsiType? = PsiPrimitiveType.getUnboxedType(type)

    internal fun stripClass(type: String): String = type.split(".").last()

}
package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.CraftsNetVersionUtils
import de.craftsblock.craftsnet.intellijplugin.utils.versioning.FeatureFlag

class FeatureFlagSpecificInspectionRule<T : CustomInspectionRule>(
    ruleIfPresent: T,
    ruleIfAbsent: T,
    val featureFlag: FeatureFlag
) : ProjectToggleableInspectionRule<T>(
    ruleIfPresent, ruleIfAbsent,
    function = function@{ project, rule ->
        val flagAvailable = CraftsNetVersionUtils.isFeatureFlagAvailable(project, featureFlag)
        return@function if (flagAvailable) rule.rule1 else rule.rule2
    }
)
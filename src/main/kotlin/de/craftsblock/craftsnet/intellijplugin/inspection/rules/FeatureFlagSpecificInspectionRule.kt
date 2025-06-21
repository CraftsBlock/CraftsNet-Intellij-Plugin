package de.craftsblock.craftsnet.intellijplugin.inspection.rules

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.CraftsNetVersionUtils
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag

class FeatureFlagSpecificInspectionRule(
    ruleIfPresent: CustomInspectionRule,
    ruleIfAbsent: CustomInspectionRule,
    val featureFlag: FeatureFlag
) : ProjectToggleableInspectionRule(
    ruleIfPresent, ruleIfAbsent,
    function = function@{ project, rule ->
        val flagAvailable = CraftsNetVersionUtils.isFeatureFlagAvailable(project, featureFlag)
        return@function if (flagAvailable) rule.rule1 else rule.rule2
    }
)
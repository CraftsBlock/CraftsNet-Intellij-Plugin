package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.FeatureFlagSpecificInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.DynamicUrlParamInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.craftsnet.TransformerInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct.MethodReturnTypeInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.direct.RequireImplementationInspectionRule
import de.craftsblock.craftsnet.intellijplugin.uitls.versioning.FeatureFlag

class RouteInspection : CustomAnnotatedInspection(
    "Route",
    "de.craftsblock.craftsnet.api.http.annotations.Route",
    DynamicUrlParamInspectionRule(),
    ParameterInspectionRule(0, "exchange", "de.craftsblock.craftsnet.api.http.Exchange"),
    RequireImplementationInspectionRule("de.craftsblock.craftsnet.api.http.RequestHandler"),
    TransformerInspectionRule(),
    FeatureFlagSpecificInspectionRule(
        MethodReturnTypeInspectionRule("*"),
        MethodReturnTypeInspectionRule("void"),
        FeatureFlag.PRINTING_RETURN_VALUES
    ),
)
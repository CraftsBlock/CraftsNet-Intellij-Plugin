package de.craftsblock.craftsnet.intellijplugin.inspection.builtin

import de.craftsblock.craftsnet.intellijplugin.inspection.CustomAnnotatedInspection
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.DynamicUrlParamInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.ParameterInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.RequireImplementationInspectionRule
import de.craftsblock.craftsnet.intellijplugin.inspection.rules.TransformerInspectionRule

class RouteInspection : CustomAnnotatedInspection(
    "Route",
    "de.craftsblock.craftsnet.api.http.annotations.Route",
    DynamicUrlParamInspectionRule(),
    ParameterInspectionRule(0, "exchange", "de.craftsblock.craftsnet.api.http.Exchange"),
    RequireImplementationInspectionRule("de.craftsblock.craftsnet.api.http.RequestHandler"),
    TransformerInspectionRule(),
)
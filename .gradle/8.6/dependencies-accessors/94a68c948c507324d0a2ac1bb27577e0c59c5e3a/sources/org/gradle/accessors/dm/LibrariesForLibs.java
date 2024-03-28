package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final EuLibraryAccessors laccForEuLibraryAccessors = new EuLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>eu</b>
     */
    public EuLibraryAccessors getEu() {
        return laccForEuLibraryAccessors;
    }

    /**
     * Group of libraries at <b>org</b>
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComDlscLibraryAccessors laccForComDlscLibraryAccessors = new ComDlscLibraryAccessors(owner);
        private final ComEsriLibraryAccessors laccForComEsriLibraryAccessors = new ComEsriLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.dlsc</b>
         */
        public ComDlscLibraryAccessors getDlsc() {
            return laccForComDlscLibraryAccessors;
        }

        /**
         * Group of libraries at <b>com.esri</b>
         */
        public ComEsriLibraryAccessors getEsri() {
            return laccForComEsriLibraryAccessors;
        }

    }

    public static class ComDlscLibraryAccessors extends SubDependencyFactory {
        private final ComDlscFormsfxLibraryAccessors laccForComDlscFormsfxLibraryAccessors = new ComDlscFormsfxLibraryAccessors(owner);

        public ComDlscLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.dlsc.formsfx</b>
         */
        public ComDlscFormsfxLibraryAccessors getFormsfx() {
            return laccForComDlscFormsfxLibraryAccessors;
        }

    }

    public static class ComDlscFormsfxLibraryAccessors extends SubDependencyFactory {
        private final ComDlscFormsfxFormsfxLibraryAccessors laccForComDlscFormsfxFormsfxLibraryAccessors = new ComDlscFormsfxFormsfxLibraryAccessors(owner);

        public ComDlscFormsfxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.dlsc.formsfx.formsfx</b>
         */
        public ComDlscFormsfxFormsfxLibraryAccessors getFormsfx() {
            return laccForComDlscFormsfxFormsfxLibraryAccessors;
        }

    }

    public static class ComDlscFormsfxFormsfxLibraryAccessors extends SubDependencyFactory {

        public ComDlscFormsfxFormsfxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.dlsc.formsfx:formsfx-core</b> coordinates and
         * with version reference <b>com.dlsc.formsfx.formsfx.core</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("com.dlsc.formsfx.formsfx.core");
        }

    }

    public static class ComEsriLibraryAccessors extends SubDependencyFactory {
        private final ComEsriArcgisruntimeLibraryAccessors laccForComEsriArcgisruntimeLibraryAccessors = new ComEsriArcgisruntimeLibraryAccessors(owner);

        public ComEsriLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.esri.arcgisruntime</b>
         */
        public ComEsriArcgisruntimeLibraryAccessors getArcgisruntime() {
            return laccForComEsriArcgisruntimeLibraryAccessors;
        }

    }

    public static class ComEsriArcgisruntimeLibraryAccessors extends SubDependencyFactory {
        private final ComEsriArcgisruntimeArcgisLibraryAccessors laccForComEsriArcgisruntimeArcgisLibraryAccessors = new ComEsriArcgisruntimeArcgisLibraryAccessors(owner);

        public ComEsriArcgisruntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.esri.arcgisruntime.arcgis</b>
         */
        public ComEsriArcgisruntimeArcgisLibraryAccessors getArcgis() {
            return laccForComEsriArcgisruntimeArcgisLibraryAccessors;
        }

    }

    public static class ComEsriArcgisruntimeArcgisLibraryAccessors extends SubDependencyFactory {
        private final ComEsriArcgisruntimeArcgisJavaLibraryAccessors laccForComEsriArcgisruntimeArcgisJavaLibraryAccessors = new ComEsriArcgisruntimeArcgisJavaLibraryAccessors(owner);

        public ComEsriArcgisruntimeArcgisLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.esri.arcgisruntime.arcgis.java</b>
         */
        public ComEsriArcgisruntimeArcgisJavaLibraryAccessors getJava() {
            return laccForComEsriArcgisruntimeArcgisJavaLibraryAccessors;
        }

    }

    public static class ComEsriArcgisruntimeArcgisJavaLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ComEsriArcgisruntimeArcgisJavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>java</b> with <b>com.esri.arcgisruntime:arcgis-java</b> coordinates and
         * with version reference <b>com.esri.arcgisruntime.arcgis.java</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("com.esri.arcgisruntime.arcgis.java");
        }

        /**
         * Dependency provider for <b>jnilibs</b> with <b>com.esri.arcgisruntime:arcgis-java-jnilibs</b> coordinates and
         * with version reference <b>com.esri.arcgisruntime.arcgis.java.jnilibs</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJnilibs() {
            return create("com.esri.arcgisruntime.arcgis.java.jnilibs");
        }

        /**
         * Dependency provider for <b>resources</b> with <b>com.esri.arcgisruntime:arcgis-java-resources</b> coordinates and
         * with version reference <b>com.esri.arcgisruntime.arcgis.java.resources</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getResources() {
            return create("com.esri.arcgisruntime.arcgis.java.resources");
        }

    }

    public static class EuLibraryAccessors extends SubDependencyFactory {
        private final EuHansoloLibraryAccessors laccForEuHansoloLibraryAccessors = new EuHansoloLibraryAccessors(owner);

        public EuLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>eu.hansolo</b>
         */
        public EuHansoloLibraryAccessors getHansolo() {
            return laccForEuHansoloLibraryAccessors;
        }

    }

    public static class EuHansoloLibraryAccessors extends SubDependencyFactory {

        public EuHansoloLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>tilesfx</b> with <b>eu.hansolo:tilesfx</b> coordinates and
         * with version reference <b>eu.hansolo.tilesfx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTilesfx() {
            return create("eu.hansolo.tilesfx");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgControlsfxLibraryAccessors laccForOrgControlsfxLibraryAccessors = new OrgControlsfxLibraryAccessors(owner);
        private final OrgJunitLibraryAccessors laccForOrgJunitLibraryAccessors = new OrgJunitLibraryAccessors(owner);
        private final OrgKordampLibraryAccessors laccForOrgKordampLibraryAccessors = new OrgKordampLibraryAccessors(owner);
        private final OrgOpenjfxLibraryAccessors laccForOrgOpenjfxLibraryAccessors = new OrgOpenjfxLibraryAccessors(owner);
        private final OrgSlf4jLibraryAccessors laccForOrgSlf4jLibraryAccessors = new OrgSlf4jLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.controlsfx</b>
         */
        public OrgControlsfxLibraryAccessors getControlsfx() {
            return laccForOrgControlsfxLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.junit</b>
         */
        public OrgJunitLibraryAccessors getJunit() {
            return laccForOrgJunitLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.kordamp</b>
         */
        public OrgKordampLibraryAccessors getKordamp() {
            return laccForOrgKordampLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.openjfx</b>
         */
        public OrgOpenjfxLibraryAccessors getOpenjfx() {
            return laccForOrgOpenjfxLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.slf4j</b>
         */
        public OrgSlf4jLibraryAccessors getSlf4j() {
            return laccForOrgSlf4jLibraryAccessors;
        }

    }

    public static class OrgControlsfxLibraryAccessors extends SubDependencyFactory {

        public OrgControlsfxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>controlsfx</b> with <b>org.controlsfx:controlsfx</b> coordinates and
         * with version reference <b>org.controlsfx.controlsfx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getControlsfx() {
            return create("org.controlsfx.controlsfx");
        }

    }

    public static class OrgJunitLibraryAccessors extends SubDependencyFactory {
        private final OrgJunitJupiterLibraryAccessors laccForOrgJunitJupiterLibraryAccessors = new OrgJunitJupiterLibraryAccessors(owner);

        public OrgJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.junit.jupiter</b>
         */
        public OrgJunitJupiterLibraryAccessors getJupiter() {
            return laccForOrgJunitJupiterLibraryAccessors;
        }

    }

    public static class OrgJunitJupiterLibraryAccessors extends SubDependencyFactory {
        private final OrgJunitJupiterJunitLibraryAccessors laccForOrgJunitJupiterJunitLibraryAccessors = new OrgJunitJupiterJunitLibraryAccessors(owner);

        public OrgJunitJupiterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.junit.jupiter.junit</b>
         */
        public OrgJunitJupiterJunitLibraryAccessors getJunit() {
            return laccForOrgJunitJupiterJunitLibraryAccessors;
        }

    }

    public static class OrgJunitJupiterJunitLibraryAccessors extends SubDependencyFactory {
        private final OrgJunitJupiterJunitJupiterLibraryAccessors laccForOrgJunitJupiterJunitJupiterLibraryAccessors = new OrgJunitJupiterJunitJupiterLibraryAccessors(owner);

        public OrgJunitJupiterJunitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.junit.jupiter.junit.jupiter</b>
         */
        public OrgJunitJupiterJunitJupiterLibraryAccessors getJupiter() {
            return laccForOrgJunitJupiterJunitJupiterLibraryAccessors;
        }

    }

    public static class OrgJunitJupiterJunitJupiterLibraryAccessors extends SubDependencyFactory {

        public OrgJunitJupiterJunitJupiterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>org.junit.jupiter:junit-jupiter-api</b> coordinates and
         * with version reference <b>org.junit.jupiter.junit.jupiter.api</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("org.junit.jupiter.junit.jupiter.api");
        }

        /**
         * Dependency provider for <b>engine</b> with <b>org.junit.jupiter:junit-jupiter-engine</b> coordinates and
         * with version reference <b>org.junit.jupiter.junit.jupiter.engine</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEngine() {
            return create("org.junit.jupiter.junit.jupiter.engine");
        }

    }

    public static class OrgKordampLibraryAccessors extends SubDependencyFactory {
        private final OrgKordampIkonliLibraryAccessors laccForOrgKordampIkonliLibraryAccessors = new OrgKordampIkonliLibraryAccessors(owner);

        public OrgKordampLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.kordamp.ikonli</b>
         */
        public OrgKordampIkonliLibraryAccessors getIkonli() {
            return laccForOrgKordampIkonliLibraryAccessors;
        }

    }

    public static class OrgKordampIkonliLibraryAccessors extends SubDependencyFactory {
        private final OrgKordampIkonliIkonliLibraryAccessors laccForOrgKordampIkonliIkonliLibraryAccessors = new OrgKordampIkonliIkonliLibraryAccessors(owner);

        public OrgKordampIkonliLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.kordamp.ikonli.ikonli</b>
         */
        public OrgKordampIkonliIkonliLibraryAccessors getIkonli() {
            return laccForOrgKordampIkonliIkonliLibraryAccessors;
        }

    }

    public static class OrgKordampIkonliIkonliLibraryAccessors extends SubDependencyFactory {

        public OrgKordampIkonliIkonliLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>javafx</b> with <b>org.kordamp.ikonli:ikonli-javafx</b> coordinates and
         * with version reference <b>org.kordamp.ikonli.ikonli.javafx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJavafx() {
            return create("org.kordamp.ikonli.ikonli.javafx");
        }

    }

    public static class OrgOpenjfxLibraryAccessors extends SubDependencyFactory {
        private final OrgOpenjfxJavafxLibraryAccessors laccForOrgOpenjfxJavafxLibraryAccessors = new OrgOpenjfxJavafxLibraryAccessors(owner);

        public OrgOpenjfxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.openjfx.javafx</b>
         */
        public OrgOpenjfxJavafxLibraryAccessors getJavafx() {
            return laccForOrgOpenjfxJavafxLibraryAccessors;
        }

    }

    public static class OrgOpenjfxJavafxLibraryAccessors extends SubDependencyFactory {

        public OrgOpenjfxJavafxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>controls</b> with <b>org.openjfx:javafx-controls</b> coordinates and
         * with version reference <b>org.openjfx.javafx.controls</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getControls() {
            return create("org.openjfx.javafx.controls");
        }

        /**
         * Dependency provider for <b>fxml</b> with <b>org.openjfx:javafx-fxml</b> coordinates and
         * with version reference <b>org.openjfx.javafx.fxml</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getFxml() {
            return create("org.openjfx.javafx.fxml");
        }

        /**
         * Dependency provider for <b>swing</b> with <b>org.openjfx:javafx-swing</b> coordinates and
         * with version reference <b>org.openjfx.javafx.swing</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSwing() {
            return create("org.openjfx.javafx.swing");
        }

        /**
         * Dependency provider for <b>web</b> with <b>org.openjfx:javafx-web</b> coordinates and
         * with version reference <b>org.openjfx.javafx.web</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getWeb() {
            return create("org.openjfx.javafx.web");
        }

    }

    public static class OrgSlf4jLibraryAccessors extends SubDependencyFactory {
        private final OrgSlf4jSlf4jLibraryAccessors laccForOrgSlf4jSlf4jLibraryAccessors = new OrgSlf4jSlf4jLibraryAccessors(owner);

        public OrgSlf4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.slf4j.slf4j</b>
         */
        public OrgSlf4jSlf4jLibraryAccessors getSlf4j() {
            return laccForOrgSlf4jSlf4jLibraryAccessors;
        }

    }

    public static class OrgSlf4jSlf4jLibraryAccessors extends SubDependencyFactory {

        public OrgSlf4jSlf4jLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>nop</b> with <b>org.slf4j:slf4j-nop</b> coordinates and
         * with version reference <b>org.slf4j.slf4j.nop</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getNop() {
            return create("org.slf4j.slf4j.nop");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final EuVersionAccessors vaccForEuVersionAccessors = new EuVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com</b>
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.eu</b>
         */
        public EuVersionAccessors getEu() {
            return vaccForEuVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org</b>
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComDlscVersionAccessors vaccForComDlscVersionAccessors = new ComDlscVersionAccessors(providers, config);
        private final ComEsriVersionAccessors vaccForComEsriVersionAccessors = new ComEsriVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.dlsc</b>
         */
        public ComDlscVersionAccessors getDlsc() {
            return vaccForComDlscVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.com.esri</b>
         */
        public ComEsriVersionAccessors getEsri() {
            return vaccForComEsriVersionAccessors;
        }

    }

    public static class ComDlscVersionAccessors extends VersionFactory  {

        private final ComDlscFormsfxVersionAccessors vaccForComDlscFormsfxVersionAccessors = new ComDlscFormsfxVersionAccessors(providers, config);
        public ComDlscVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.dlsc.formsfx</b>
         */
        public ComDlscFormsfxVersionAccessors getFormsfx() {
            return vaccForComDlscFormsfxVersionAccessors;
        }

    }

    public static class ComDlscFormsfxVersionAccessors extends VersionFactory  {

        private final ComDlscFormsfxFormsfxVersionAccessors vaccForComDlscFormsfxFormsfxVersionAccessors = new ComDlscFormsfxFormsfxVersionAccessors(providers, config);
        public ComDlscFormsfxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.dlsc.formsfx.formsfx</b>
         */
        public ComDlscFormsfxFormsfxVersionAccessors getFormsfx() {
            return vaccForComDlscFormsfxFormsfxVersionAccessors;
        }

    }

    public static class ComDlscFormsfxFormsfxVersionAccessors extends VersionFactory  {

        public ComDlscFormsfxFormsfxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.dlsc.formsfx.formsfx.core</b> with value <b>11.6.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCore() { return getVersion("com.dlsc.formsfx.formsfx.core"); }

    }

    public static class ComEsriVersionAccessors extends VersionFactory  {

        private final ComEsriArcgisruntimeVersionAccessors vaccForComEsriArcgisruntimeVersionAccessors = new ComEsriArcgisruntimeVersionAccessors(providers, config);
        public ComEsriVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.esri.arcgisruntime</b>
         */
        public ComEsriArcgisruntimeVersionAccessors getArcgisruntime() {
            return vaccForComEsriArcgisruntimeVersionAccessors;
        }

    }

    public static class ComEsriArcgisruntimeVersionAccessors extends VersionFactory  {

        private final ComEsriArcgisruntimeArcgisVersionAccessors vaccForComEsriArcgisruntimeArcgisVersionAccessors = new ComEsriArcgisruntimeArcgisVersionAccessors(providers, config);
        public ComEsriArcgisruntimeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.esri.arcgisruntime.arcgis</b>
         */
        public ComEsriArcgisruntimeArcgisVersionAccessors getArcgis() {
            return vaccForComEsriArcgisruntimeArcgisVersionAccessors;
        }

    }

    public static class ComEsriArcgisruntimeArcgisVersionAccessors extends VersionFactory  {

        private final ComEsriArcgisruntimeArcgisJavaVersionAccessors vaccForComEsriArcgisruntimeArcgisJavaVersionAccessors = new ComEsriArcgisruntimeArcgisJavaVersionAccessors(providers, config);
        public ComEsriArcgisruntimeArcgisVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.esri.arcgisruntime.arcgis.java</b>
         */
        public ComEsriArcgisruntimeArcgisJavaVersionAccessors getJava() {
            return vaccForComEsriArcgisruntimeArcgisJavaVersionAccessors;
        }

    }

    public static class ComEsriArcgisruntimeArcgisJavaVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        public ComEsriArcgisruntimeArcgisJavaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.esri.arcgisruntime.arcgis.java</b> with value <b>200.3.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("com.esri.arcgisruntime.arcgis.java"); }

        /**
         * Version alias <b>com.esri.arcgisruntime.arcgis.java.jnilibs</b> with value <b>200.3.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJnilibs() { return getVersion("com.esri.arcgisruntime.arcgis.java.jnilibs"); }

        /**
         * Version alias <b>com.esri.arcgisruntime.arcgis.java.resources</b> with value <b>200.3.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getResources() { return getVersion("com.esri.arcgisruntime.arcgis.java.resources"); }

    }

    public static class EuVersionAccessors extends VersionFactory  {

        private final EuHansoloVersionAccessors vaccForEuHansoloVersionAccessors = new EuHansoloVersionAccessors(providers, config);
        public EuVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.eu.hansolo</b>
         */
        public EuHansoloVersionAccessors getHansolo() {
            return vaccForEuHansoloVersionAccessors;
        }

    }

    public static class EuHansoloVersionAccessors extends VersionFactory  {

        public EuHansoloVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>eu.hansolo.tilesfx</b> with value <b>11.48</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getTilesfx() { return getVersion("eu.hansolo.tilesfx"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgControlsfxVersionAccessors vaccForOrgControlsfxVersionAccessors = new OrgControlsfxVersionAccessors(providers, config);
        private final OrgJunitVersionAccessors vaccForOrgJunitVersionAccessors = new OrgJunitVersionAccessors(providers, config);
        private final OrgKordampVersionAccessors vaccForOrgKordampVersionAccessors = new OrgKordampVersionAccessors(providers, config);
        private final OrgOpenjfxVersionAccessors vaccForOrgOpenjfxVersionAccessors = new OrgOpenjfxVersionAccessors(providers, config);
        private final OrgSlf4jVersionAccessors vaccForOrgSlf4jVersionAccessors = new OrgSlf4jVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.controlsfx</b>
         */
        public OrgControlsfxVersionAccessors getControlsfx() {
            return vaccForOrgControlsfxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.junit</b>
         */
        public OrgJunitVersionAccessors getJunit() {
            return vaccForOrgJunitVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.kordamp</b>
         */
        public OrgKordampVersionAccessors getKordamp() {
            return vaccForOrgKordampVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.openjfx</b>
         */
        public OrgOpenjfxVersionAccessors getOpenjfx() {
            return vaccForOrgOpenjfxVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.slf4j</b>
         */
        public OrgSlf4jVersionAccessors getSlf4j() {
            return vaccForOrgSlf4jVersionAccessors;
        }

    }

    public static class OrgControlsfxVersionAccessors extends VersionFactory  {

        public OrgControlsfxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.controlsfx.controlsfx</b> with value <b>11.1.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getControlsfx() { return getVersion("org.controlsfx.controlsfx"); }

    }

    public static class OrgJunitVersionAccessors extends VersionFactory  {

        private final OrgJunitJupiterVersionAccessors vaccForOrgJunitJupiterVersionAccessors = new OrgJunitJupiterVersionAccessors(providers, config);
        public OrgJunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.junit.jupiter</b>
         */
        public OrgJunitJupiterVersionAccessors getJupiter() {
            return vaccForOrgJunitJupiterVersionAccessors;
        }

    }

    public static class OrgJunitJupiterVersionAccessors extends VersionFactory  {

        private final OrgJunitJupiterJunitVersionAccessors vaccForOrgJunitJupiterJunitVersionAccessors = new OrgJunitJupiterJunitVersionAccessors(providers, config);
        public OrgJunitJupiterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.junit.jupiter.junit</b>
         */
        public OrgJunitJupiterJunitVersionAccessors getJunit() {
            return vaccForOrgJunitJupiterJunitVersionAccessors;
        }

    }

    public static class OrgJunitJupiterJunitVersionAccessors extends VersionFactory  {

        private final OrgJunitJupiterJunitJupiterVersionAccessors vaccForOrgJunitJupiterJunitJupiterVersionAccessors = new OrgJunitJupiterJunitJupiterVersionAccessors(providers, config);
        public OrgJunitJupiterJunitVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.junit.jupiter.junit.jupiter</b>
         */
        public OrgJunitJupiterJunitJupiterVersionAccessors getJupiter() {
            return vaccForOrgJunitJupiterJunitJupiterVersionAccessors;
        }

    }

    public static class OrgJunitJupiterJunitJupiterVersionAccessors extends VersionFactory  {

        public OrgJunitJupiterJunitJupiterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.junit.jupiter.junit.jupiter.api</b> with value <b>5.10.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getApi() { return getVersion("org.junit.jupiter.junit.jupiter.api"); }

        /**
         * Version alias <b>org.junit.jupiter.junit.jupiter.engine</b> with value <b>5.10.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEngine() { return getVersion("org.junit.jupiter.junit.jupiter.engine"); }

    }

    public static class OrgKordampVersionAccessors extends VersionFactory  {

        private final OrgKordampIkonliVersionAccessors vaccForOrgKordampIkonliVersionAccessors = new OrgKordampIkonliVersionAccessors(providers, config);
        public OrgKordampVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.kordamp.ikonli</b>
         */
        public OrgKordampIkonliVersionAccessors getIkonli() {
            return vaccForOrgKordampIkonliVersionAccessors;
        }

    }

    public static class OrgKordampIkonliVersionAccessors extends VersionFactory  {

        private final OrgKordampIkonliIkonliVersionAccessors vaccForOrgKordampIkonliIkonliVersionAccessors = new OrgKordampIkonliIkonliVersionAccessors(providers, config);
        public OrgKordampIkonliVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.kordamp.ikonli.ikonli</b>
         */
        public OrgKordampIkonliIkonliVersionAccessors getIkonli() {
            return vaccForOrgKordampIkonliIkonliVersionAccessors;
        }

    }

    public static class OrgKordampIkonliIkonliVersionAccessors extends VersionFactory  {

        public OrgKordampIkonliIkonliVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.kordamp.ikonli.ikonli.javafx</b> with value <b>12.3.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJavafx() { return getVersion("org.kordamp.ikonli.ikonli.javafx"); }

    }

    public static class OrgOpenjfxVersionAccessors extends VersionFactory  {

        private final OrgOpenjfxJavafxVersionAccessors vaccForOrgOpenjfxJavafxVersionAccessors = new OrgOpenjfxJavafxVersionAccessors(providers, config);
        public OrgOpenjfxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.openjfx.javafx</b>
         */
        public OrgOpenjfxJavafxVersionAccessors getJavafx() {
            return vaccForOrgOpenjfxJavafxVersionAccessors;
        }

    }

    public static class OrgOpenjfxJavafxVersionAccessors extends VersionFactory  {

        public OrgOpenjfxJavafxVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.openjfx.javafx.controls</b> with value <b>21</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getControls() { return getVersion("org.openjfx.javafx.controls"); }

        /**
         * Version alias <b>org.openjfx.javafx.fxml</b> with value <b>21</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getFxml() { return getVersion("org.openjfx.javafx.fxml"); }

        /**
         * Version alias <b>org.openjfx.javafx.swing</b> with value <b>21</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSwing() { return getVersion("org.openjfx.javafx.swing"); }

        /**
         * Version alias <b>org.openjfx.javafx.web</b> with value <b>21</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getWeb() { return getVersion("org.openjfx.javafx.web"); }

    }

    public static class OrgSlf4jVersionAccessors extends VersionFactory  {

        private final OrgSlf4jSlf4jVersionAccessors vaccForOrgSlf4jSlf4jVersionAccessors = new OrgSlf4jSlf4jVersionAccessors(providers, config);
        public OrgSlf4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.slf4j.slf4j</b>
         */
        public OrgSlf4jSlf4jVersionAccessors getSlf4j() {
            return vaccForOrgSlf4jSlf4jVersionAccessors;
        }

    }

    public static class OrgSlf4jSlf4jVersionAccessors extends VersionFactory  {

        public OrgSlf4jSlf4jVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.slf4j.slf4j.nop</b> with value <b>2.0.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNop() { return getVersion("org.slf4j.slf4j.nop"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}

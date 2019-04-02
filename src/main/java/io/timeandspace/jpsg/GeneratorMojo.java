/*
 * Copyright JPSG Maven Plugin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.timeandspace.jpsg;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mojo(name = "jpsg", defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE, requiresProject = true)
public class GeneratorMojo extends AbstractMojo {

    @Parameter
    protected String defaultTypes;

    @Parameter
    protected List<String> with;

    @Parameter
    protected List<String> processors;

    @Parameter
    protected List<String> never;

    @Parameter
    protected List<String> excludes;

    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "${basedir}/src/main/javaTemplates")
    private File sourceDirectory;

    @Parameter(defaultValue = "${project.build.directory}/generated-sources/jpsg")
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        Generator g = new Generator();

        if (defaultTypes != null && !defaultTypes.isEmpty())
            g.setDefaultTypes(defaultTypes);
        if (with != null)
            g.with(with);
        if (processors != null){
            for (String processor : processors) {
                g.addProcessor(processor);
            }
        }
        if (never != null)
            g.never(never);
        if (excludes != null)
            g.exclude(excludes);
        g.setSource(sourceDirectory);
        g.setTarget(outputDirectory);

        try {
            g.generate();
        } catch (IOException e) {
            throw new MojoExecutionException("Error during JPSG generation", e);
        }

        if (project != null) {
            project.addCompileSourceRoot(outputDirectory.getPath());
        }
    }
}

package org.wikidata.wdtk.dumpfiles.renderer.constraint;

/*
 * #%L
 * Wikidata Toolkit Dump File Handling
 * %%
 * Copyright (C) 2014 Wikidata Toolkit Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.dumpfiles.constraint.Constraint;
import org.wikidata.wdtk.dumpfiles.constraint.ConstraintTargetRequiredClaim;
import org.wikidata.wdtk.dumpfiles.renderer.format.RendererFormat;

/**
 * 
 * @author Julian Mendez
 * 
 */
class ConstraintTargetRequiredClaimRenderer implements ConstraintRenderer {

	final RendererFormat f;

	public ConstraintTargetRequiredClaimRenderer(RendererFormat rendererFormat) {
		this.f = rendererFormat;
	}

	@Override
	public void renderConstraint(Constraint c) {
		if (c instanceof ConstraintTargetRequiredClaim) {
			render((ConstraintTargetRequiredClaim) c);
		}
	}

	public void render(ConstraintTargetRequiredClaim c) {
		render(c.getConstrainedProperty(), c.getProperty(), c.getItem());
	}

	public void render(PropertyIdValue p, PropertyIdValue r, ItemIdValue q) {
		if ((p == null) || (r == null)) {
			return;
		}
		this.f.addDeclarationObjectProperty(this.f.getPs(p));
		this.f.addDeclarationObjectProperty(this.f.getPv(p));
		this.f.addDeclarationObjectProperty(this.f.getPs(r));

		this.f.addInverseFunctionalObjectProperty(this.f.getPs(p));
		if (q == null) {
			this.f.addObjectPropertyRange(
					this.f.getPv(p),
					this.f.getObjectSomeValuesFrom(this.f.getPs(r),
							this.f.owlThing()));
		} else {
			this.f.addDeclarationObjectProperty(this.f.getPv(r));
			this.f.addDeclarationNamedIndividual(this.f.getItem(q));

			this.f.addObjectPropertyRange(this.f.getPv(p), this.f
					.getObjectSomeValuesFrom(this.f.getPs(r), this.f
							.getObjectSomeValuesFrom(this.f.getPv(r),
									this.f.getObjectOneOf(this.f.getItem(q)))));
		}
	}

}

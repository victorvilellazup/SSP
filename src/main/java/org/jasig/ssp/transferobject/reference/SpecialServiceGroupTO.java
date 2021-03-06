/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reference;

import com.google.common.collect.Lists;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.transferobject.TransferObject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SpecialServiceGroupTO extends AbstractReferenceTO<SpecialServiceGroup>
		implements TransferObject<SpecialServiceGroup> { // NOPMD

	private String code;
    private boolean notifyOnWithdraw = false;

	public SpecialServiceGroupTO() {
		super();
	}

	public SpecialServiceGroupTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public SpecialServiceGroupTO(final SpecialServiceGroup model) {
		super();
		from(model);
        code = model.getCode();
        notifyOnWithdraw = model .isNotifyOnWithdraw();
    }

    @Override
    public final void from(final SpecialServiceGroup model) {
        if (model == null) {
            throw new IllegalArgumentException("Model can not be null.");
        }

        super.from(model);
        code = model.getCode();
        notifyOnWithdraw = model .isNotifyOnWithdraw();
    }

	public static List<SpecialServiceGroupTO> toTOList(final Collection<SpecialServiceGroup> models) {
		final List<SpecialServiceGroupTO> tObjects = Lists.newArrayList();
		for (final SpecialServiceGroup model : models) {
			tObjects.add(new SpecialServiceGroupTO(model));// NOPMD
		}

		return tObjects;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isNotifyOnWithdraw() {
        return notifyOnWithdraw;
    }

    public void setNotifyOnWithdraw(boolean notifyOnWithdraw) {
        this.notifyOnWithdraw = notifyOnWithdraw;
    }
}
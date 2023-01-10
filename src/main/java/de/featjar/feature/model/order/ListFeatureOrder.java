/*
 * Copyright (C) 2023 Elias Kuiter
 *
 * This file is part of FeatJAR-feature-model.
 *
 * feature-model is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License,
 * or (at your option) any later version.
 *
 * feature-model is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with feature-model. If not, see <https://www.gnu.org/licenses/>.
 *
 * See <https://github.com/FeatureIDE/FeatJAR-model> for further information.
 */
package de.featjar.feature.model.order;

import de.featjar.feature.model.IFeature;
import de.featjar.feature.model.mixins.IHasFeatureTree;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListFeatureOrder extends AFeatureOrder {
    protected final List<IFeature> featureList;

    public ListFeatureOrder(List<IFeature> featureList) {
        this.featureList = featureList;
    }

    @Override
    public List<IFeature> apply(IHasFeatureTree featureModel) {
        return Stream.concat(
                        featureList.stream().filter(featureModel.getFeatures()::contains),
                        featureModel.getFeatures().stream().filter(feature -> !featureList.contains(feature)))
                .collect(Collectors.toList());
    }
}
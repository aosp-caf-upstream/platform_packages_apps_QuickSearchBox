/*
 * Copyright (C) 2009 The Android Open Source Project
 *
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
 */

package com.android.quicksearchbox;

import android.os.Handler;

import java.util.ArrayList;

/**
 * A suggestions provider that gets suggestions from all enabled sources that
 * want to be included in global search.
 */
public class GlobalSuggestionsProvider extends AbstractSuggestionsProvider {

    private final SourceLookup mSources;

    private final ShortcutRepository mShortcutRepo;

    public GlobalSuggestionsProvider(Config config, SourceLookup sources,
            SourceTaskExecutor queryExecutor,
            Handler publishThread,
            Promoter promoter,
            ShortcutRepository shortcutRepo) {
        super(config, queryExecutor, publishThread, promoter);
        mSources = sources;
        mShortcutRepo = shortcutRepo;
    }

    // TODO: Order sources based on click stats.
    // TODO: Cache this list?
    public ArrayList<Source> getOrderedSources() {
        ArrayList<Source> orderedSources = new ArrayList<Source>();
        Source webSource = mSources.getSelectedWebSearchSource();
        if (webSource != null) {
            orderedSources.add(webSource);
        }
        orderedSources.addAll(mSources.getEnabledSources());
        return orderedSources;
    }

    @Override
    protected SuggestionCursor getShortcutsForQuery(String query) {
        if (mShortcutRepo == null) return null;
        return mShortcutRepo.getShortcutsForQuery(query);
    }

}

package com.softtech.android.displaydata;

import com.softtech.android.displaydata.utils.StringResources;

public class Enums {
    public enum FilterType{
        NONE,
        BY_COUNTRY
    }

    public enum SortType{
        NONE(0){
            @Override
            public String toString() {
                return "None";
            }
        },
        BY_BROWSERNAME(1) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_browsername);
            }
        },
        BY_PLATFORM(2) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_platform);
            }
        },
        BY_COUNTRY(3) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_country);
            }
        },
        BY_RATING_ASCENDING(4) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_rating_asc);
            }
        },
        BY_RATING_DESCENDING(5) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_rating_desc);
            }
        },
        BY_PERFORMANCE_ASCENDING(6) {
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_performance_asc);
            }
        },
        BY_PERFORMANCE_DESCENDING(7){
            @Override
            public String toString() {
                return StringResources.loadString(R.string.sort_by_performance_desc);
            }
        };

        private final int value;

        private SortType(int value) {
            this.value = value;
        }

        public int getVal(){
            return this.value;
        }


        public static SortType fromInteger(int x) {
            switch(x) {
                case 0:
                    return NONE;
                case 1:
                    return BY_BROWSERNAME;
                case 2:
                    return BY_PLATFORM;
                case 3:
                    return BY_COUNTRY;
                case 4:
                    return BY_RATING_ASCENDING;
                case 5:
                    return BY_RATING_DESCENDING;
                case 6:
                    return BY_PERFORMANCE_ASCENDING;
                case 7:
                    return BY_PERFORMANCE_DESCENDING;
            }
            return null;
        }
    }
}

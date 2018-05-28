package bw.com.donyin.utils;

import android.text.TextUtils;

import java.util.List;

import bw.com.donyin.utils.impl.AdwHomeBadger;
import bw.com.donyin.utils.impl.ApexHomeBadger;
import bw.com.donyin.utils.impl.AsusHomeLauncher;
import bw.com.donyin.utils.impl.DefaultBadger;
import bw.com.donyin.utils.impl.LGHomeBadger;
import bw.com.donyin.utils.impl.NewHtcHomeBadger;
import bw.com.donyin.utils.impl.NovaHomeBadger;
import bw.com.donyin.utils.impl.SamsungHomeBadger;
import bw.com.donyin.utils.impl.SolidHomeBadger;
import bw.com.donyin.utils.impl.SonyHomeBadger;
import bw.com.donyin.utils.impl.XiaomiHomeBadger;

public enum BadgerType {
    DEFAULT {
        @Override
        public Badger initBadger() {
            return new DefaultBadger();
        }
    }, ADW {
        @Override
        public Badger initBadger() {
            return new AdwHomeBadger();
        }
    }, APEX {
        @Override
        public Badger initBadger() {
            return new ApexHomeBadger();
        }
    }, ASUS {
        @Override
        public Badger initBadger() {
            return new AsusHomeLauncher();
        }
    }, LG {
        @Override
        public Badger initBadger() {
            return new LGHomeBadger();
        }
    }, HTC {
        @Override
        public Badger initBadger() {
            return new NewHtcHomeBadger();
        }
    }, NOVA {
        @Override
        public Badger initBadger() {
            return new NovaHomeBadger();
        }
    }, SAMSUNG {
        @Override
        public Badger initBadger() {
            return new SamsungHomeBadger();
        }
    }, SOLID {
        @Override
        public Badger initBadger() {
            return new SolidHomeBadger();
        }
    }, SONY {
        @Override
        public Badger initBadger() {
            return new SonyHomeBadger();
        }
    }, XIAO_MI {
        @Override
        public Badger initBadger() {
            return new XiaomiHomeBadger();
        }
    };

    public Badger badger;

    public static Badger getBadgerByLauncherName(String launcherName) {
        Badger result = new DefaultBadger();
        if (!TextUtils.isEmpty(launcherName))
            for (BadgerType badgerType : BadgerType.values()) {
                if (badgerType.getSupportLaunchers().contains(launcherName)) {
                    result = badgerType.getBadger();
                    break;
                }
            }
        return result;
    }

    public Badger getBadger() {
        if (badger == null)
            badger = initBadger();
        return badger;
    }

    public abstract Badger initBadger();

    public List<String> getSupportLaunchers() {
        return getBadger().getSupportLaunchers();
    }
}

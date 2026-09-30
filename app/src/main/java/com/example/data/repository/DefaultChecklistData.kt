package com.example.data.repository

import com.example.data.model.ChecklistItemEntity

object DefaultChecklistData {
  val CHANNELS = listOf("TV3", "8TV", "TV9", "NTV7", "DS")

  data class ParameterSettingSpec(
    val id: String,
    val channel: String,
    val assignment: String
  )

  val PARAMETER_SETTINGS = listOf(
    ParameterSettingSpec(
      id = "tv3-parameter-setting",
      channel = "TV3",
      assignment = "KAPSUL SURAH-SURAH YASSIN TANYALAH USTAZ 6AM & 6.30AM → CG (TV9_HD)/TV9 LOGO; MBUZZ / RECORDED & LIVE PROGRAM → CG (TV3_HD)/ TV3 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; NEWS/KAPSUL/KAPSUL SURAH/BACAAN AL-QURAN (5AM)/ALIF SOFIA/KALAMULLAH/WOW SHOP CAPSULE → CG (TV3_HD)/ TV3 LOGO; Maggie x WHI PETI CHEF (2 MINS) → CG (TV3_HD)/ TV3 LOGO; SLOT MIKRO DRAMA (2mins) 11.30am → CG (TV3_HD)/ TV3 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; WHI VIBE (5 MINS) → CG (TV3_HD)/ TV3 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE (No Station ID & No Classification); Cross over KJH - LOGO ONLY - (if xover after BTerkini put station ident as buffer); WAKTU BERBUKA/WAKTU IMSAK - NO SETTING; KALAMULLAH → CG (TV3_HD)/ TV3 LOGO; TAKBIR RAYA - LOGO ONLY; PENGUMUMAN PUASA → GENERIC JOB DEVICE; SUARA SIAPA NEXT LEVEL (10MINS) - AHAD 10:30PM → LOGO ONLY; HELO DOKTOR GIGI (10MINS) - LOGO ONLY; Chamsarang : Not Found Love (Seg 2) : Off trigger; Perutusan PM- TV3 LOGO; Kembara Komuniti Masjid-SEL 9:50PM (10MINS) - TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; Kembara Komuniti Masjid-AHD 7:45AM (10MINS) - TV3 LOGO; 2nd Segment (Break Seg) / Filler Prog. → CG (TV3_HD)/ TV3 LOGO / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM); Prog. Continue after Nightline → as per normal program & change number of segment after Nightline; Program change in PB → change ingest ID at Subtitle section accordingly and note in Playlist Hardcopy; make sure ingest ID at Subtitle section is without number of segment; Program with subtitle DRAG manually in PB → untick “Inherit from event”."
    ),
    ParameterSettingSpec(
      id = "8tv-parameter-setting",
      channel = "8TV",
      assignment = "RECORDED PROG → CG (8TV_HD)/ 8TV LOGO / TRIGGER (VIZRT) / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM) - RECTANGLE GRAPHIC; LIVE PROG → CG (8TV_HD)/ 8TV LOGO, TRIGGER DEVICE & GENERIC JOB DEVICE; 8TV Morning Express Mon-Fri 9:30am → CG (8TV_HD)/ 8TV LOGO, TRIGGER DEVICE & GENERIC JOB DEVICE; MIDDAY NEWS 12.30PM / MANDARIN NEWS 8PM → CG (8TV_HD)/ 8TV LOGO, TRIGGER DEVICE & GENERIC JOB DEVICE; VC/CAPSULE/8FM@8TV/LIVING DELIGHT HIGHLIGHTS/1728HRS WOW SHOP (2MINS)/TITAH UCAPAN AGONG/PM → CG (8TV_HD)/ 8TV LOGO; PENGUMUMAN PUASA → GENERIC JOB DEVICE; PENGISYTIHARAN HARI RAYA AIDILADHA - GENERIC JOB DEVICE ONLY; Perutusan PM- 8TV LOGO; 2nd Segment (Break Seg) / Filler Prog. → CG (8TV_HD) / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM); Program change in PB → change ingest ID at Subtitle section accordingly and note in Playlist Hardcopy; make sure ingest ID at Subtitle section is without number of segment; Program with subtitle DRAG manually in PB → untick “Inherit from event”."
    ),
    ParameterSettingSpec(
      id = "tv9-parameter-setting",
      channel = "TV9",
      assignment = "KAPSUL SURAH (1 & 2 SEGMENTS), BACAAN YASSIN TANYALAH USTAZ 6.25AM & 5.55AM → CG (TV9_HD)/TV9 LOGO; RECORDED PROG/LIVE PROGRAM → CG (TV9_HD)/TV9 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; BULETIN TV9 → NO SETTING; MY #QURANTIME 2.0 LIVE&RECORDED → CG (TV9_HD)/TV9 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; SURAH/KAPSUL/KOOL FM@TV9/KUIZ PACAK/KAPSUL TANYALAH USTAZ/TITAH UCAPAN AGONG-PM → CG (TV9_HD)/TV9 LOGO; Branded Content (10mins) → CG (TV9_HD)/ TV9 LOGO; Program TV9/Filler TV9 duration 11mins and above - Setting as program; Program TV9/Filler TV9 duration below 11mins - Logo only; Cross over KJH - LOGO ONLY (if back to back with LIVE, put station ident as buffer); OBTV/LITTLE AMMAR/MINA MILA → CG (TV9_HD)/TV9 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; Perutusan PM- TV9 LOGO; KAPSUL MASA/KAPSUL MALAYSIA/RAHSIA AL-QURAN → LOGO ONLY; IKON LOKAL → CG (TV9_HD)/TV9 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; PENGUMUMAN PUASA → GENERIC JOB DEVICE; PENGISYTIHARAN HARI RAYA AIDILADHA - GENERIC JOB DEVICE ONLY; TAKBIR RAYA - LOGO ONLY; WAKTU BERBUKA/WAKTU IMSAK - NO SETTING; SOLAT DARI MASJIDIL HARAM - SETTING AS NORMAL PROGRAM; 2nd Segment (Break Seg) → CG (TV9_HD)/ TV9 LOGO / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM) - RECTANGLE GRAPHIC; Program change in PB → change ingest ID at Subtitle section accordingly and note in Playlist Hardcopy; make sure ingest ID at Subtitle section is without number of segment; Program with subtitle DRAG manually in PB → untick “Inherit from event”."
    ),
    ParameterSettingSpec(
      id = "ntv7-parameter-setting",
      channel = "NTV7",
      assignment = "RECORDED PROG → CG (NTV7_HD)/ NTV7 LOGO / TRIGGER (VIZRT) / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM) - RECTANGLE GRAPHIC; LIVE PROGRAM → CG (NTV7_HD)/ NTV7 LOGO, TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; CAPSULE / HOTFM@DIDIKTV / KAPSUL KLINIKDIDIK+ → CG (NTV7_HD)/ NTV7 LOGO; BULETIN DIDIK 1PM (RECORDED/REPEAT) - LOGO ONLY; BULETIN DIDIK 7PM - LOGO ONLY; REFLEKSI MINGGUAN - TRIGGER DEVICE (VIZRT) & GENERIC JOB DEVICE; PROGRAM KHAS - CG (NTV7_HD)/ NTV7 LOGO & GENERIC JOB DEVICE; WAKTU BERBUKA/WAKTU IMSAK - NO SETTING; TAKBIR RAYA - LOGO ONLY; Perutusan PM- NTV7 LOGO; BELANJAWAN → CG (NTV7_HD)/ NTV7 LOGO & GENERIC JOB DEVICE; PENGUMUMAN PUASA → GENERIC JOB DEVICE; PENGISYTIHARAN HARI RAYA AIDILADHA - GENERIC JOB DEVICE ONLY; 2nd Segment (Break Seg) / Filler Prog. → CG (NTV7_HD)/ NTV7 LOGO / GENERIC JOB DEVICE / SUBTITLE (POLISTREAM) - RECTANGLE GRAPHIC; Program change in PB → change ingest ID at Subtitle section accordingly and note in Playlist Hardcopy; make sure ingest ID at Subtitle section is without number of segment; Program with subtitle DRAG manually in PB → untick “Inherit from event”."
    ),
    ParameterSettingSpec(
      id = "ds-parameter-setting",
      channel = "DS",
      assignment = "CJWOW → NO SETTING; 2nd Segment (Break Seg) → CG (TV3DS)/ DS LOGO; Program change in PB → change ingest ID at Subtitle section and note in Playlist Hardcopy; Program with subtitle key in manually in PB → untick “Inherit from event”."
    )
  )

  fun getInitialItems(date: String = "2026-02-24"): List<ChecklistItemEntity> {
    val paramMap = PARAMETER_SETTINGS.associateBy { it.channel }

    return listOf(
      // TV3
      ChecklistItemEntity(
        id = "tv3-1",
        channel = "TV3",
        item = "Closing station 12.30am",
        assignment = "Negaraku placement - If Mega Movie extends after Nightline, schedule Negaraku after Mega Movie. Station Id, Al-Fatihah (3P079622), SI015070 starting 7/2/26",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-2",
        channel = "TV3",
        item = "Assign Waktu Solat",
        assignment = "Zohor · Asar · Maghrib · Subuh: SI015064 · SI015065 · SI015066 · SI015067. Confirm version and timing before lock.",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-3",
        channel = "TV3",
        item = "Promo Buletin Utama",
        assignment = "SI000565 · STD A - 40s · 3 spots. Place at 6pm, 7pm and 7.30pm. Use third break for 5.30pm Sat–Sun slot.",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-4",
        channel = "TV3",
        item = "Promo Nightline",
        assignment = "SI014968 · STD A - 30s · 2 spots. Use first and third break at 11pm slot. Schedule 11pm and 11.30pm.",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-5",
        channel = "TV3",
        item = "Opening Station 6.30am",
        assignment = "##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26, Al-Fatihah, Raudhah Ident, Classification Bacaan Yaasin Tanyalah Ustaz (U)",
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-6",
        channel = "TV3",
        item = "Lead In To News",
        assignment = "Sebentar Lagi, St. ID & TV3 Clockface. Selepas Ini Bul. Pagi 9am – SI014993, New Countdown Buletin Utama 8pm – SI015001",
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv3-7",
        channel = "TV3",
        item = "Live Program",
        assignment = "Source & Total Segment. Double check Live Source if Back to Back. STD A",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = paramMap["TV3"]!!.id,
        channel = "TV3",
        item = "Parameter Setting",
        assignment = paramMap["TV3"]!!.assignment,
        status = "Not started",
        scheduleDate = date
      ),

      // 8TV
      ChecklistItemEntity(
        id = "8tv-1",
        channel = "8TV",
        item = "Opening Station 7am",
        assignment = "##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26; 8TV Good Morning Ident - SI015005",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "8tv-2",
        channel = "8TV",
        item = "Closing Station",
        assignment = "8TV Good Night Ident - SI015006; ##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "8tv-3",
        channel = "8TV",
        item = "Station ID",
        assignment = "8TV Good Morning Ident 2002 - SI015005 (from 6-10.30am only)",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "8tv-4",
        channel = "8TV",
        item = "Promo Midday Mand News 30s",
        assignment = "1 spot per day (11:30am) SI004495; Mon - Sun : Assign before LPF for CJ WOW/DRAMA 11:30am",
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "8tv-5",
        channel = "8TV",
        item = "Promo Mand News 40s - STD D",
        assignment = "2 spots per day (around 7:00 & 7:30pm) SI000430; Daily: 1st spot in 1st break and 3rd break for 7pm slot",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "8tv-6",
        channel = "8TV",
        item = "8TV Morning Express",
        assignment = "STD D, Comm + Promo = 2mins, A1 = 1mins (2 Segments 1 Breaks)",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = paramMap["8TV"]!!.id,
        channel = "8TV",
        item = "Parameter Setting",
        assignment = paramMap["8TV"]!!.assignment,
        status = "Not started",
        scheduleDate = date
      ),

      // TV9
      ChecklistItemEntity(
        id = "tv9-1",
        channel = "TV9",
        item = "Closing Station - 12.00am",
        assignment = "PROMO / PSA; STATION IDENT NEW - SI015012; ##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv9-2",
        channel = "TV9",
        item = "Opening Station starting 1/1/2026",
        assignment = "STATION COUNTDOWN NEW - SI015013; ##OP/CL STATION:NEGARAKU 2026## - SI015070",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv9-3",
        channel = "TV9",
        item = "Assign Waktu Solat",
        assignment = "WAKTU ZOHOR - SI015064, WAKTU ASAR - SI015065, WAKTU MAGHRIB - SI015066, WAKTU SUBUH - SI015067",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv9-4",
        channel = "TV9",
        item = "BERITA TV9 7PM",
        assignment = "Must end at 7:50pm sharp, no overrun. STD C starting 14/10, 4 breaks",
        status = "Needs attention",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "tv9-5",
        channel = "TV9",
        item = "Promo Berita TV9 7pm Highlight 30s",
        assignment = "3 spots per day - STD C; Starting 25/2/26 1700pm (Before program) & 1800pm",
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = paramMap["TV9"]!!.id,
        channel = "TV9",
        item = "Parameter Setting",
        assignment = paramMap["TV9"]!!.assignment,
        status = "Not started",
        scheduleDate = date
      ),

      // NTV7
      ChecklistItemEntity(
        id = "ntv7-1",
        channel = "NTV7",
        item = "IBS",
        assignment = "IBS",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-2",
        channel = "NTV7",
        item = "Opening Station (START WITH HOTFM/WOW)",
        assignment = "##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26; LPF U",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-3",
        channel = "NTV7",
        item = "Opening Station ( NOT DIDIK TITLE )",
        assignment = "##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26; DIDIK TV LOGO IDENT - SI015068; LPF ( CLASSIFICATION )",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-4",
        channel = "NTV7",
        item = "Opening Station (START WITH DIDIK TV)",
        assignment = "##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26; DIDIK TV LOGO IDENT - SI015068; DIDIK TV_MONTAGE - SI015059",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-5",
        channel = "NTV7",
        item = "Closing Station - 12.00am (before HOTFM)",
        assignment = "DIDIK TV LOGO IDENT - SI015068; ##OP/CL STATION:NEGARAKU 2026## - SI015070 starting 7/2/26",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-6",
        channel = "NTV7",
        item = "Assign Waktu Solat",
        assignment = "WAKTU ZOHOR - SI015064; WAKTU ASAR - SI015065; WAKTU MAGHRIB - SI015066",
        status = "Needs attention",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ntv7-7",
        channel = "NTV7",
        item = "DIDIK TV SLOT/BULETIN DIDIK",
        assignment = "NO LPF. Before every slot to put DIDIK TV LOGO IDENT follow by DIDIK TV_MONTAGE -GP",
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = paramMap["NTV7"]!!.id,
        channel = "NTV7",
        item = "Parameter Setting",
        assignment = paramMap["NTV7"]!!.assignment,
        status = "Not started",
        scheduleDate = date
      ),

      // DS
      ChecklistItemEntity(
        id = "ds-1",
        channel = "DS",
        item = "IBS",
        assignment = "IBS",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-2",
        channel = "DS",
        item = "Promo",
        assignment = "Insert Promo & FN",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-3",
        channel = "DS",
        item = "Assign Waktu Solat",
        assignment = "WAKTU ZOHOR - SI015064; WAKTU ASAR - SI015065; WAKTU MAGHRIB - SI015066; WAKTU SUBUH - SI015067",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-4",
        channel = "DS",
        item = "PEBBLE BEACH",
        assignment = "PEBBLE BEACH",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-5",
        channel = "DS",
        item = "Mode Assignment",
        assignment = "FIX - Opening Title (Program) & Waktu Solat; RECORDED PROG → CG (TV3DS )/ DS LOGO",
        status = "In review",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = paramMap["DS"]!!.id,
        channel = "DS",
        item = "Parameter Setting",
        assignment = paramMap["DS"]!!.assignment,
        status = "Not started",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-7",
        channel = "DS",
        item = "Waktu Solat",
        assignment = "Zohor : SI015064; Asar : SI015065; Maghrib:SI015066; Subuh:SI015067; double-check timing & ID of waktu solat",
        status = "Needs attention",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-8",
        channel = "DS",
        item = "Red Barrel",
        assignment = "to rectify/ alert BSU",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-9",
        channel = "DS",
        item = "Modify & Save",
        assignment = "Save for backup (CHECK LATEST DATE & TIME MODIFY)",
        status = "Completed",
        scheduleDate = date
      ),
      ChecklistItemEntity(
        id = "ds-10",
        channel = "DS",
        item = "INFO",
        assignment = "INFO",
        status = "Not started",
        scheduleDate = date
      )
    )
  }
}

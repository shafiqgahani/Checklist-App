package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.ItemStatus
import com.example.data.repository.DefaultChecklistData
import com.example.ui.components.parseParameterSettings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Checklist Ops", appName)
  }

  @Test
  fun `verify status cycling order`() {
    assertEquals(ItemStatus.IN_REVIEW, ItemStatus.COMPLETED.nextStatus())
    assertEquals(ItemStatus.NEEDS_ATTENTION, ItemStatus.IN_REVIEW.nextStatus())
    assertEquals(ItemStatus.NOT_STARTED, ItemStatus.NEEDS_ATTENTION.nextStatus())
    assertEquals(ItemStatus.COMPLETED, ItemStatus.NOT_STARTED.nextStatus())
  }

  @Test
  fun `verify channels initial data present`() {
    val items = DefaultChecklistData.getInitialItems("2026-02-24")
    val channels = items.map { it.channel }.distinct()
    assertTrue(channels.contains("TV3"))
    assertTrue(channels.contains("8TV"))
    assertTrue(channels.contains("TV9"))
    assertTrue(channels.contains("NTV7"))
    assertTrue(channels.contains("DS"))
  }

  @Test
  fun `verify each channel contains exactly one Parameter Setting item with stable IDs`() {
    val items = DefaultChecklistData.getInitialItems("2026-02-24")
    val channels = listOf("TV3", "8TV", "TV9", "NTV7", "DS")

    val expectedIds = mapOf(
      "TV3" to "tv3-parameter-setting",
      "8TV" to "8tv-parameter-setting",
      "TV9" to "tv9-parameter-setting",
      "NTV7" to "ntv7-parameter-setting",
      "DS" to "ds-parameter-setting"
    )

    for (channel in channels) {
      val channelParamItems = items.filter {
        it.channel.equals(channel, ignoreCase = true) && it.item == "Parameter Setting"
      }
      assertEquals("Channel $channel must have exactly 1 Parameter Setting item", 1, channelParamItems.size)

      val paramItem = channelParamItems.first()
      assertEquals(expectedIds[channel], paramItem.id)
      assertEquals("Not started", paramItem.status)
      assertTrue("Assignment must not be empty", paramItem.assignment.isNotBlank())
    }
  }

  @Test
  fun `verify DS parameter setting is distinct and not used for other channels`() {
    val items = DefaultChecklistData.getInitialItems("2026-02-24")
    val dsParam = items.find { it.id == "ds-parameter-setting" }
    assertNotNull(dsParam)

    val tv3Param = items.find { it.id == "tv3-parameter-setting" }
    val tv8Param = items.find { it.id == "8tv-parameter-setting" }
    val tv9Param = items.find { it.id == "tv9-parameter-setting" }
    val ntv7Param = items.find { it.id == "ntv7-parameter-setting" }

    assertNotNull(tv3Param)
    assertNotNull(tv8Param)
    assertNotNull(tv9Param)
    assertNotNull(ntv7Param)

    assertNotEquals(dsParam!!.assignment, tv3Param!!.assignment)
    assertNotEquals(dsParam.assignment, tv8Param!!.assignment)
    assertNotEquals(dsParam.assignment, tv9Param!!.assignment)
    assertNotEquals(dsParam.assignment, ntv7Param!!.assignment)

    assertTrue(tv3Param.assignment.contains("Maggie x WHI PETI CHEF"))
    assertTrue(tv8Param.assignment.contains("8TV Morning Express"))
    assertTrue(tv9Param.assignment.contains("MY #QURANTIME 2.0"))
    assertTrue(ntv7Param.assignment.contains("DIDIKTV"))
    assertTrue(dsParam.assignment.contains("CJWOW → NO SETTING"))
  }

  @Test
  fun `verify parameter setting parsing for all channels`() {
    val items = DefaultChecklistData.getInitialItems("2026-02-24")
    val channels = listOf("TV3", "8TV", "TV9", "NTV7", "DS")

    for (channel in channels) {
      val paramItem = items.first { it.channel == channel && it.item == "Parameter Setting" }
      val parsedRows = parseParameterSettings(paramItem.assignment)
      assertTrue("Channel $channel must have parsed rows", parsedRows.isNotEmpty())

      // Verify row numbering is 1-indexed and consecutive
      parsedRows.forEachIndexed { index, rowItem ->
        assertEquals(index + 1, rowItem.rowNumber)
        if (rowItem.isOperationalNote) {
          assertTrue("Operational note text must not be empty", rowItem.rawText.isNotBlank())
        } else {
          assertTrue("Condition must not be empty", rowItem.condition.isNotBlank())
          assertTrue("Setting must not be empty", rowItem.setting.isNotBlank())
        }
      }
    }

    // Verify TV3 specific parsing
    val tv3Item = items.first { it.channel == "TV3" && it.item == "Parameter Setting" }
    val tv3Rows = parseParameterSettings(tv3Item.assignment)
    val firstTv3Row = tv3Rows.first()
    assertEquals("KAPSUL SURAH-SURAH YASSIN TANYALAH USTAZ 6AM & 6.30AM", firstTv3Row.condition)
    assertEquals("CG (TV9_HD)/TV9 LOGO", firstTv3Row.setting)
    assertFalse(firstTv3Row.isOperationalNote)

    // Verify row with NO SETTING detected
    val noSettingRow = tv3Rows.find { it.containsNoSetting }
    assertNotNull(noSettingRow)
    assertTrue(noSettingRow!!.rawText.contains("NO SETTING"))

    // Verify row with LOGO ONLY detected
    val logoOnlyRow = tv3Rows.find { it.containsLogoOnly }
    assertNotNull(logoOnlyRow)
    assertTrue(logoOnlyRow!!.rawText.contains("LOGO ONLY"))

    // Verify DS parsing
    val dsItem = items.first { it.channel == "DS" && it.item == "Parameter Setting" }
    val dsRows = parseParameterSettings(dsItem.assignment)
    assertEquals(4, dsRows.size)
    assertEquals("CJWOW", dsRows[0].condition)
    assertEquals("NO SETTING", dsRows[0].setting)
    assertTrue(dsRows[0].containsNoSetting)
  }
}

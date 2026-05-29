import type { Ref } from "vue"
import type { IonContent } from "@ionic/vue"

type IonContentInstance = InstanceType<typeof IonContent>

type ScrollContainer =
  | IonContentInstance
  | {
      $el?: {
        scrollToTop?: (duration?: number) => void
      }
    }

const isIonContent = (value: unknown): value is IonContentInstance =>
  !!value && typeof (value as IonContentInstance).scrollToTop === "function"

const hasScrollToTop = (
  value: unknown
): value is { scrollToTop: (duration?: number) => void } =>
  !!value &&
  typeof value === "object" &&
  "scrollToTop" in value &&
  typeof (value as { scrollToTop: unknown }).scrollToTop === "function"

export const updateIonContentRef = (
  target: Ref<IonContentInstance | null>,
  refValue: unknown
) => {
  if (isIonContent(refValue)) {
    target.value = refValue
    return
  }

  if (refValue && typeof refValue === "object" && "$el" in refValue) {
    target.value = refValue as IonContentInstance
    return
  }

  if (refValue === null) {
    target.value = null
  }
}

export const scrollIonContentToTop = (content: ScrollContainer | null) => {
  if (!content) {
    return
  }

  if (isIonContent(content)) {
    content.scrollToTop(0)
    return
  }

  const el = (content as { $el?: unknown }).$el
  if (hasScrollToTop(el)) {
    el.scrollToTop(0)
  }
}

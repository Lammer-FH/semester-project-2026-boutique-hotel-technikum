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
        return;
    }

    if (isIonContent(content)) {
        // Cast content to bypass the incomplete IonContentInstance type
        const nativeContent = content as unknown as { scrollToTop: (duration?: number) => void };
        nativeContent.scrollToTop(0);
        return;
    }

    // Cast the fallback to bypass Vue's standard HTMLElement type
    const fallback = content as { $el?: { scrollToTop?: (duration?: number) => void } };
    fallback.$el?.scrollToTop?.(0);
};
